package org.alex.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import org.alex.alexlibrary.R;


/**
 * 作者：Alex
 * 时间：2016年09月04日    08:55
 * 简述：
 */

@SuppressWarnings("all")
public class CircleImageView extends ImageView {
    /**
     * 绘图的Paint
     */
    private Paint paint;
    /**
     * 圆角的半径
     */
    private int radius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix matrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader bitmapShader;
    /**
     * view的宽度
     */
    private int width;

    private String cropType;

    /**外圆的宽度*/
    private float outCircleWidth;
    /**外圆的颜色*/
    private int outCircleColor;
    private Paint outCirclePaint;
    /**内圆的宽度*/
    private float innerCircleWidth;
    /**内圆的颜色*/
    private int innerCircleColor;
    private Paint innerCirclePaint;
    public CircleImageView(Context context) {
        super(context);
        initView(null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    /**
     * 初始化 视图
     */
    private void initView( AttributeSet attrs) {
        Context context = getContext();
        cropType = CropType.centerTop;
        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            outCircleWidth = typedArray.getDimension(R.styleable.CircleImageView_outCircleWidth, dp2px(4));
            outCircleColor = typedArray.getColor(R.styleable.CircleImageView_outCircleColor, Color.parseColor("#CCCCCC"));
            innerCircleWidth = typedArray.getDimension(R.styleable.CircleImageView_innerCircleWidth, dp2px(4));
            innerCircleColor = typedArray.getColor(R.styleable.CircleImageView_innerCircleColor, Color.parseColor("#CCCCCCCC"));
            int cropType = typedArray.getInteger(R.styleable.CircleImageView_cropType, 0);
            if(cropType == 0 ){
                this.cropType = CropType.centerTop;
            }else if(cropType == 1 ){
                this.cropType = CropType.leftTop;
            }else if(cropType == 2){
                this.cropType = CropType.center;
            }
        }
        matrix = new Matrix();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        radius = (int) dp2px(10);
        outCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outCirclePaint.setColor(outCircleColor);
        outCirclePaint.setStrokeWidth(outCircleWidth);
		/*消除锯齿  */
        outCirclePaint.setAntiAlias(true);
		/*绘制空心圆  */
        outCirclePaint.setStyle(Paint.Style.STROKE);
        outCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(innerCircleColor);
        innerCirclePaint.setStrokeWidth(innerCircleWidth);
		/*消除锯齿  */
        innerCirclePaint.setAntiAlias(true);
		/*绘制空心圆  */
        innerCirclePaint.setStyle(Paint.Style.STROKE);
        innerCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = width / 2;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        if (getDrawable() == null) {
            return;
        }
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        initBitmapShader();
        canvas.drawCircle(radius, radius, radius - outCircleWidth, paint);
        canvas.drawCircle(radius, radius, radius - outCircleWidth*0.5F, outCirclePaint);
        canvas.drawCircle(radius, radius, radius-outCircleWidth - innerCircleWidth*0.5F, innerCirclePaint);
    }

    /**
     * 初始化BitmapShader
     */
    private void initBitmapShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawable2Bitmap(drawable);
        bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
        float scale = width * 1.0f / bSize;
        matrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawable.setBounds(0, 0, w, h);
        if ((w >= h) && CropType.centerTop.equals(cropType)) {
            canvas.translate((h - w) * 0.5F, 0);
        } else if ((w >= h) && CropType.center.equals(cropType)) {
            canvas.translate((h - w) * 0.5F, 0);
        } else if ((w < h) && CropType.center.equals(cropType)) {
            canvas.translate(0, (w - h) * 0.5F);
        }
        drawable.draw(canvas);
        return bitmap;
    }

    public static final class CropType {
        public static final String leftTop = "起点在左上角";
        public static final String centerTop = "起点水平居中&垂直置顶";
        public static final String center = "起点在图片中心";
    }

    /**
     * 数据转换: dp---->px
     */
    private float dp2px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}

