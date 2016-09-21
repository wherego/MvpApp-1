package org.alex.dashlineview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class DashLineView extends View {
    private Paint paint;
    private Path path;
    /**
     * 虚线的 宽度 - 长度
     */
    private float dashWidth;

    /**
     * 虚线的 高度
     */
    private float dashHeight;

    /**虚线的颜色*/
    private int dashColor;
    /**虚线之间的 间距*/
    private float dashGap;
    private int height;

    public DashLineView(Context context) {
        super(context);
    }

    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        dashWidth = dpToPx(10);
        dashHeight = dpToPx(2.5F);
        dashGap = dashWidth;
        dashColor = Color.parseColor("#BBBBBB");
        paint = new Paint();
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);//设置画笔style空心
        paint.setColor(dashColor);
        paint.setStrokeWidth(dashHeight);//设置画笔宽度
        path.moveTo(dashGap/2, 0);
        path.lineTo(getWidth(), 0);
        PathEffect effects = new DashPathEffect(new float[]{dashWidth, dashGap}, 10);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }

    /**
     * 虚线的 宽度 - 长度
     * @param dashWidth 单位 dp
     */
    public void setDashWidth(float dashWidth){
        this.dashWidth = dpToPx(dashWidth);
        invalidate();
    }

    /**
     * 设置虚线的 颜色
     * */
    public void setDashColor(int dashColor){
        this.dashColor = dashColor;
        paint.setColor(dashColor);
        invalidate();
    }

    /**
     * 设置虚线之间的间距
     * @param dashGap 单位 dp
     * */
    public void setDashGap(float dashGap){
        this.dashGap = dpToPx(dashGap);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);
        this.height = height;
        dashHeight = height * 2;
        setMeasuredDimension(width, height);
    }
    /**
     * 测量宽和高，这一块可以是一个模板代码(Android群英传)
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureSize(int widthMeasureSpec) {
        int result = 0;
        //从MeasureSpec对象中提取出来具体的测量模式和大小
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            //测量的模式，精确
            result = size;
        }else {
            result = (int) dpToPx(2);
        }
        return result;
    }

    /**
     * 数据转换: dp---->px
     */
    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }
}
