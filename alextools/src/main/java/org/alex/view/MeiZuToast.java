package org.alex.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者：Alex
 * 时间：2016/9/6 10:06
 * 简述：
 */
@SuppressWarnings("all")
public class MeiZuToast extends Toast {
    private TextView textView;
    private Context context;

    public MeiZuToast(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        textView = new TextView(context);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        int dp8 = dp2px(8);
        textView.setPadding(dp8, dp8, dp8, dp8);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        GradientDrawable gradientDrawableNormal = new GradientDrawable();
        gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
        gradientDrawableNormal.setColor(Color.parseColor("#99353535"));
        float radius = dp2px(4);
        gradientDrawableNormal.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        textView.setBackgroundDrawable(gradientDrawableNormal);
        setView(textView);
    }

    @Override
    public void setText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("  ");
        }
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("  ");
        }
    }

    public static MeiZuToast makeText(Context context, String text, int duration) {
        MeiZuToast meiZuToast = new MeiZuToast(context);
        meiZuToast.setText(text);
        meiZuToast.setDuration(duration);
        return meiZuToast;
    }

    /**
     * 数据转换: dp---->px
     */
    private int dp2px(float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


}
