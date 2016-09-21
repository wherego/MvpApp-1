package org.alex.dialog.base;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

import org.alex.dialog.anim.BaseAnimatorSet;
import org.alex.dialog.annotation.AnimType;

/**
 * 作者：Alex
 * 时间：2016年09月10日    12:56
 * 简述：
 */

public abstract class TaoBaoDialog<D extends TaoBaoDialog> extends SimpleDialog<D> {
    private BaseAnimatorSet inAnimSet;
    private BaseAnimatorSet outAnimSet;
    private View contentView;
    protected int duration;
    protected int backgroundColor;
    private View parentView;

    public TaoBaoDialog(Context context, View contentView, int theme) {
        super(context, theme);
        this.contentView = contentView;
        initView();
    }

    protected void initView() {
        duration = 300;
        backgroundColor = Color.parseColor("#111111");
        parentView = (View) contentView.getParent();
        inAnimSet = new WindowsInAs();
        outAnimSet = new WindowsOutAs();
    }

    public D setBackgroundColor(@NonNull String backgroundColor){
        try {
            setBackgroundColor(Color.parseColor(backgroundColor));
        }catch (Exception e){

        }
        return (D)this;
    }
    public D setBackgroundColor(@ColorInt int backgroundColor){
        this.backgroundColor = backgroundColor;
        return (D)this;
    }

    /**
     * 显示对话框，强制转换对话框的动画类型
     */
    @Override
    public void show() {
        show(AnimType.BOTTOM_2_TOP);
    }
    /**
     * 显示对话框，强制转换对话框的动画类型
     *
     * @param animType
     */
    @Override
    public void show(@AnimType int animType) {
        super.show(AnimType.BOTTOM_2_TOP);
        parentView.setBackgroundColor(backgroundColor);
        if (contentView != null && inAnimSet != null) {
            inAnimSet.duration(duration).playOn(contentView);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                parentView.setBackgroundColor(0);
            }
        }.sendEmptyMessageDelayed(100, duration);
        if (contentView != null && outAnimSet != null) {
            outAnimSet.duration(duration).playOn(contentView);
        }
    }

    private final class WindowsInAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
            rotationX.setStartDelay(200);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.8f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.8f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "rotationX", 0f, 10).setDuration(200),
                    rotationX,
                    ObjectAnimator.ofFloat(view, "translationY", 0, -0.1f * displayMetrics.heightPixels).setDuration(350)
            );
        }
    }

    private final class WindowsOutAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
            rotationX.setStartDelay(200);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.0f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.0f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "rotationX", 0f, 10).setDuration(200),
                    rotationX,
                    ObjectAnimator.ofFloat(view, "translationY", -0.1f * displayMetrics.heightPixels, 0).setDuration(350)
            );
        }
    }

    /**
     * 销毁一切资源
     */
    @Override
    public void destroy() {
        super.destroy();
        inAnimSet = null;
        outAnimSet = null;
        contentView.destroyDrawingCache();
        contentView = null;
        parentView.destroyDrawingCache();
        parentView = null;
    }
}
