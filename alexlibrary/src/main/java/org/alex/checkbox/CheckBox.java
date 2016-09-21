package org.alex.checkbox;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.alex.callback.SimpleAnimatorListener;

import org.alex.alexlibrary.R;

/**
 * 作者：Alex
 * 时间：2016/9/8 12:57
 * 简述：
 */
public class CheckBox extends View {
    /**
     * 复选框 默认 大小
     */
    private int defaultSize = 40;
    /**
     * 控件的 尺寸
     */
    private int size;

    /**
     * 最外层的圆角矩形
     */
    private RectF outBorderRectF;

    /**
     * 最外层的圆角矩形
     */
    private Paint outBorderPaint;
    /**
     * 最外层 边框的 宽度
     */
    private float outBorderWidth;
    /**
     * 最外层 边框的 颜色
     */
    private int outBorderColor;
    /**
     * 外边框 圆角化 半径
     */
    private float outBorderRadius;

    /**
     * 内置矩形框
     */
    private RectF innerCheckedRectF;

    /**
     * 内置矩形框
     */
    private Paint innerCheckedPaint;

    /**
     * 内置矩形框 颜色
     */
    private int innerCheckedColor;
    //
    /**
     * 内置矩形框
     */
    private RectF innerNormalRectF;

    /**
     * 内置矩形框
     */
    private Paint innerNormalPaint;

    /**
     * 内置矩形框 颜色
     */
    private int innerNormalColor;

    /**
     * 对勾
     */
    private Paint checkMarkPaint;
    /**
     * 对号的4个点的坐标
     */
    private float[] pointArray;
    /**
     * 对勾的宽度
     */
    private float checkMarkWidth;
    /**
     * 对勾的 颜色
     */
    private int checkMarkColor;
    private float checkMarkerProgress;
    /**
     * 选中
     */
    private boolean isChecked;
    /**
     * 使用过渡动画
     */
    private boolean canAnim;
    /**
     * 展示 对勾 需要的时间长度
     */
    private int checkMarkerDuration;
    private ObjectAnimator checkMarkerShowAnimator;
    private ObjectAnimator checkMarkerDismissAnimator;
    private OnCheckChangeListener onCheckChangeListener;
    private static final String KEY_IS_CHECKED = "IS_CHECKED";

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        Context context = getContext();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckBox);
        outBorderColor = typedArray.getColor(R.styleable.CheckBox_cb_outBorderColor, Color.parseColor("#CCCCCC"));
        outBorderWidth = typedArray.getDimension(R.styleable.CheckBox_cb_outBorderWidth, dp2px(4));
        outBorderRadius = typedArray.getDimension(R.styleable.CheckBox_cb_outBorderRadius, dp2px(8));
        innerNormalColor = typedArray.getColor(R.styleable.CheckBox_cb_innerNormalColor, Color.parseColor("#FFFFFF"));
        innerCheckedColor = typedArray.getColor(R.styleable.CheckBox_cb_innerCheckedColor, Color.parseColor("#CCCCCC"));
        checkMarkColor = typedArray.getColor(R.styleable.CheckBox_cb_checkMarkColor, Color.parseColor("#FF5722"));
        checkMarkWidth = typedArray.getDimension(R.styleable.CheckBox_cb_checkMarkWidth, dp2px(4));
        isChecked = typedArray.getBoolean(R.styleable.CheckBox_cb_isChecked, false);
        canAnim = typedArray.getBoolean(R.styleable.CheckBox_cb_canAnim, true);
        checkMarkerDuration = typedArray.getInteger(R.styleable.CheckBox_cb_checkMarkerDuration, 400);
        typedArray.recycle();
        defaultSize = (int) dp2px(48);
        /*外边框*/
        outBorderRectF = new RectF();
        outBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outBorderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        outBorderPaint.setColor(outBorderColor);
        outBorderPaint.setStrokeWidth(outBorderWidth);
        outBorderPaint.setStyle(Paint.Style.STROKE);
        /*内置 矩形框*/
        innerCheckedRectF = new RectF();
        innerCheckedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCheckedPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        innerCheckedPaint.setColor(innerCheckedColor);
        innerCheckedPaint.setStrokeWidth(outBorderWidth);
        innerCheckedPaint.setStyle(Paint.Style.FILL);

        innerNormalRectF = new RectF();
        innerNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerNormalPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        innerNormalPaint.setColor(innerNormalColor);
        innerNormalPaint.setStrokeWidth(outBorderWidth);
        innerNormalPaint.setStyle(Paint.Style.FILL);
        /*对勾*/
        checkMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        checkMarkPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        checkMarkPaint.setColor(checkMarkColor);
        checkMarkPaint.setStrokeWidth(checkMarkWidth);
        checkMarkPaint.setStrokeCap(Paint.Cap.ROUND);
        checkMarkPaint.setStyle(Paint.Style.STROKE);
        pointArray = new float[8];
        checkMarkerProgress = (isChecked) ? 1F : 0F;
        /*动画*/
        checkMarkerShowAnimator = ObjectAnimator.ofFloat(this, "checkMarkerProgress", 0F, 1F);
        checkMarkerShowAnimator.setDuration(checkMarkerDuration);
        checkMarkerShowAnimator.addListener(new MySimpleAnimatorListener("checkMarkerShowAnimator"));

        checkMarkerDismissAnimator = ObjectAnimator.ofFloat(this, "checkMarkerProgress", 1F, 0F);
        checkMarkerDismissAnimator.setDuration(checkMarkerDuration);
        checkMarkerDismissAnimator.addListener(new MySimpleAnimatorListener("checkMarkerDismissAnimator"));
        setOnClickListener(new MyOnClickListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawInnerNormalRect(canvas);
        drawInnerCheckedRect(canvas);
        canvas.drawRoundRect(outBorderRectF, outBorderRadius, outBorderRadius, outBorderPaint);
        drawCheckMarker(canvas);
    }

    public CheckBox canAnim(boolean canAnim) {
        this.canAnim = canAnim;
        return this;
    }

    /**
     * 获得 被选择
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 设置 被选择
     */
    public CheckBox isChecked(boolean isChecked) {
        return isChecked(isChecked, false);
    }

    /**
     * 设置 被选择
     *
     * @param canAnim 使用动画
     */
    public CheckBox isChecked(boolean isChecked, boolean canAnim) {
        this.isChecked = isChecked;
        if (onCheckChangeListener != null) {
            onCheckChangeListener.OnCheckChange(isChecked, CheckBox.this);
        }
        if (!canAnim) {
            if (isChecked) {
                checkMarkerProgress = 1F;
            } else {
                checkMarkerProgress = 0F;
            }
            invalidate();
            return this;
        }
        if (isChecked) {
            checkMarkerShowAnimator.start();
        } else {
            checkMarkerDismissAnimator.start();
        }
        return this;
    }

    /**
     * 切换 被选择
     */
    public CheckBox toggleChecked() {
        toggleChecked(false);
        return this;
    }

    /**
     * 切换 被选择
     *
     * @param canAnim 使用动画
     */
    public CheckBox toggleChecked(boolean canAnim) {
        this.isChecked = !this.isChecked;
        return isChecked(isChecked, canAnim);
    }

    public CheckBox onCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
        return this;
    }

    /**
     * 画 内置矩形
     */
    private void drawInnerNormalRect(Canvas canvas) {
        float width = outBorderWidth * 0.5F;
        float checkMarkerProgress = this.checkMarkerProgress;

        innerNormalRectF.left = size * 0.5F * (checkMarkerProgress) + width;
        innerNormalRectF.right = size * (1 - checkMarkerProgress) - width;
        innerNormalRectF.top = size * 0.5F * (checkMarkerProgress) + width;
        innerNormalRectF.bottom = size * (1 - checkMarkerProgress) - width;
        canvas.drawRoundRect(innerNormalRectF, outBorderRadius, outBorderRadius, innerNormalPaint);
    }

    /**
     * 画 内置矩形
     */
    private void drawInnerCheckedRect(Canvas canvas) {
        /*内置矩形*/
        float width = outBorderWidth * 0.5F;
        float checkMarkerProgress = this.checkMarkerProgress;
        if (isChecked) {
            //checkMarkerProgress += 0.5;
        }
        if (checkMarkerProgress >= 1) {
            checkMarkerProgress = 1F;
        }
        innerCheckedRectF.left = size * 0.5F * (1 - checkMarkerProgress) + width;
        innerCheckedRectF.right = size * 0.5F * (1 + checkMarkerProgress) - width;
        innerCheckedRectF.top = size * 0.5F * (1 - checkMarkerProgress) + width;
        innerCheckedRectF.bottom = size * 0.5F * (1 + checkMarkerProgress) - width;
        canvas.drawRoundRect(innerCheckedRectF, outBorderRadius, outBorderRadius, innerCheckedPaint);
    }

    /**
     * 画 对勾
     */
    private void drawCheckMarker(Canvas canvas) {
        if (checkMarkerProgress < 1 / 2f) {
            float x = pointArray[0] + (pointArray[2] - pointArray[0]) * checkMarkerProgress;
            float y = pointArray[1] + (pointArray[3] - pointArray[1]) * checkMarkerProgress;
            canvas.drawLine(pointArray[0], pointArray[1], x, y, checkMarkPaint);
        } else {
            float x = pointArray[4] + (pointArray[6] - pointArray[4]) * checkMarkerProgress;
            float y = pointArray[5] + (pointArray[7] - pointArray[5]) * checkMarkerProgress;
            canvas.drawLine(pointArray[0], pointArray[1], pointArray[2], pointArray[3], checkMarkPaint);
            canvas.drawLine(pointArray[4], pointArray[5], x, y, checkMarkPaint);
        }
    }

    private final class MyOnClickListener implements View.OnClickListener {

        /**
         * 处理点击事件
         */
        @Override
        public void onClick(View v) {
            if (isChecked) {
                if (canAnim) {
                    checkMarkerDismissAnimator.start();
                } else {
                    isChecked(false, false);
                }
            } else {
                if (canAnim) {
                    checkMarkerShowAnimator.start();
                } else {
                    isChecked(true, false);
                }
            }
        }
    }

    private final class MySimpleAnimatorListener extends SimpleAnimatorListener {
        private String type;

        public MySimpleAnimatorListener(String type) {
            this.type = type;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            if ("checkMarkerShowAnimator".equals(type)) {
                isChecked = true;
            } else if ("checkMarkerDismissAnimator".equals(type)) {
                isChecked = false;
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if ("checkMarkerShowAnimator".equals(type)) {
                isChecked(true);
            } else if ("checkMarkerDismissAnimator".equals(type)) {
                isChecked(false);
            }
        }
    }

    private void setCheckMarkerProgress(float checkMarkerProgress) {
        this.checkMarkerProgress = checkMarkerProgress;
        invalidate();
    }

    /**
     * 外边矩形宽度，单位 dp
     */
    public CheckBox outBorderWidth(float outBorderWidth) {
        this.outBorderWidth = dp2px(outBorderWidth);
        invalidate();
        return this;
    }

    /**
     * 外边矩形颜色
     */
    public CheckBox outBorderColor(int outBorderColor) {
        this.outBorderColor = outBorderColor;
        invalidate();
        return this;
    }

    /**
     * 外边矩形颜色
     */
    public CheckBox outBorderColor(String outBorderColor) {
        this.outBorderColor = Color.parseColor(outBorderColor);
        invalidate();
        return this;
    }

    /**
     * 外边矩形圆角化半径
     */
    public CheckBox outBorderRadius(float outBorderRadius) {
        this.outBorderRadius = dp2px(outBorderRadius);
        invalidate();
        return this;
    }

    /**
     * 内置矩形默认颜色
     */
    public CheckBox innerNormalColor(int innerNormalColor) {
        this.innerNormalColor = innerNormalColor;
        invalidate();
        return this;
    }

    /**
     * 内置矩形默认颜色
     */
    public CheckBox innerNormalColor(String innerNormalColor) {
        this.innerNormalColor = Color.parseColor(innerNormalColor);
        invalidate();
        return this;
    }

    /**
     * 内置矩形Checked颜色
     */
    public CheckBox innerCheckedColor(int innerCheckedColor) {
        this.innerCheckedColor = innerCheckedColor;
        invalidate();
        return this;
    }

    /**
     * 内置矩形Checked颜色
     */
    public CheckBox innerCheckedColor(String innerCheckedColor) {
        this.innerCheckedColor = Color.parseColor(innerCheckedColor);
        invalidate();
        return this;
    }

    public CheckBox checkMarkWidth(float checkMarkWidth) {
        this.checkMarkWidth = dp2px(checkMarkWidth);
        invalidate();
        return this;
    }

    /**
     * 对勾颜色
     */
    public CheckBox checkMarkColor(int checkMarkColor) {
        this.innerCheckedColor = checkMarkColor;
        invalidate();
        return this;
    }

    /**
     * 对勾颜色
     */
    public CheckBox checkMarkColor(String checkMarkColor) {
        this.innerCheckedColor = Color.parseColor(checkMarkColor);
        invalidate();
        return this;
    }

    /**
     * 动画的过渡时间
     */
    public CheckBox checkMarkerDuration(int checkMarkerDuration) {
        this.checkMarkerDuration = checkMarkerDuration;
        invalidate();
        return this;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_IS_CHECKED, super.onSaveInstanceState());
        bundle.putBoolean(KEY_IS_CHECKED, isChecked);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            boolean isChecked = bundle.getBoolean(KEY_IS_CHECKED);
            isChecked(isChecked);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_IS_CHECKED));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
            width = height = (int) Math.min(dp2px(defaultSize) - params.leftMargin - params.rightMargin, dp2px(defaultSize) - params.bottomMargin - params.topMargin);
        }
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
        this.size = size;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float halfOutBorderWidth = outBorderWidth * 0.5F;
        outBorderRectF.left = 0 + halfOutBorderWidth;
        outBorderRectF.right = size - halfOutBorderWidth;
        outBorderRectF.top = 0 + halfOutBorderWidth;
        outBorderRectF.bottom = size - halfOutBorderWidth;
        /*对号左边细线  起点 - x */
        pointArray[0] = 121 / 600F * size;
        /*对号左边线  起点 - y */
        pointArray[1] = 309 / 600F * size;

        /*对号左边细线 终点 - x */
        pointArray[2] = 241 / 600F * size;
        /*对号左边细线 终点 - y */
        pointArray[3] = 408 / 600F * size;

        /*对号右边细线  起点 - x */
        pointArray[4] = pointArray[2];
        /*对号右边细线  起点 - y */
        pointArray[5] = pointArray[3];

        /*对号右边细线  终点 - x */
        pointArray[6] = 476 / 600F * size;
        /*对号右边细线  终点 - y */
        pointArray[7] = 181 / 600F * size;
    }

    /**
     * 数据转换: dp---->px
     */
    private float dp2px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }


}
