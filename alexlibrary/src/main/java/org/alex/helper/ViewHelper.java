package org.alex.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.alex.util.LogUtil;

import org.alex.alexlibrary.R;
import org.alex.annotation.Status;
import org.alex.dialog.LoadingDialog;
import org.alex.dialog.annotation.AnimType;
import org.alex.dialog.annotation.OnKeyType;
import org.alex.dialog.callback.SimpleOnKeyListener;
import org.alex.mvp.BaseHttpContract;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class ViewHelper {
    private LoadingDialog loadingDialog;
    private Toast toast;
    private TextView toastTextView;
    private BaseHttpContract.View view;
    private Map<String, View> layoutMap;

    private static final String sDefaultLayout = "sDefaultLayout";
    private static final String sLoadingLayout = "sLoadingLayout";
    private static final String sSuccessLayout = "sSuccessLayout";
    private static final String sFailLayout = "sFailLayout";
    private static final String sEmptyLayout = "sEmptyLayout";

    public ViewHelper(@NonNull BaseHttpContract.View view) {
        this.view = view;
    }

    /**
     * 展示延时对话框
     */
    public void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.show(AnimType.CENTER_NORMAL);
        } else {
            LogUtil.e("loadingDialog 空了");
        }
    }

    /**
     * 隐藏延时对话框
     */
    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 初始化延时对话框
     */
    public void initLoadingDialog() {
        loadingDialog = new LoadingDialog(view.getViewContext());
        loadingDialog.setOnKeyListener(new SimpleOnKeyListener((Activity) view.getViewContext(), OnKeyType.DISMISS_KILL_ACTIVITY));
    }


    /**
     * 初始化 多状态布局
     */
    public void initMultiModeBodyLayout(@NonNull Context context, @IdRes int bodyLayoutId) {
        if (bodyLayoutId == Status.NO_LAYOUT_RES_ID) {
            LogUtil.w("主布局文件为空");
            return;
        }
        if (context == null) {
            LogUtil.e("上下文为空");
            return;
        }
        layoutMap = new HashMap<>();
        View bodyLayout = view.findView(bodyLayoutId);
        ViewGroup.LayoutParams bodyParentParams = bodyLayout.getLayoutParams();
        ViewGroup bodyParentLayout = (ViewGroup) bodyLayout.getParent();
        int index = bodyParentLayout.indexOfChild(bodyLayout);
        /*view只能有一个Parent，先移除后添加*/
        bodyParentLayout.removeView(bodyLayout);
        final FrameLayout bodyFrameLayout = new FrameLayout(context);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        /*FrameLayout 第一层视图*/
        bodyFrameLayout.addView(bodyLayout);
        layoutMap.put(sSuccessLayout, bodyLayout);
        /*FrameLayout 第二层视图*/

        /**默认 布局*/
        if (view.getStatusLayoutModel().defaultLayoutId != Status.NO_LAYOUT_RES_ID) {
            View layout = LayoutInflater.from(context).inflate(view.getStatusLayoutModel().defaultLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setVisibility(View.GONE);
            layoutMap.put(sDefaultLayout, layout);
        }

        /**loading布局*/
        if (view.getStatusLayoutModel().loadingLayoutId != Status.NO_LAYOUT_RES_ID) {
            View layout = LayoutInflater.from(context).inflate(view.getStatusLayoutModel().loadingLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setVisibility(View.GONE);
            layoutMap.put(sLoadingLayout, layout);
        }

        /**空数据 布局*/
        if (view.getStatusLayoutModel().emptyLayoutId != Status.NO_LAYOUT_RES_ID) {
            View layout = LayoutInflater.from(context).inflate(view.getStatusLayoutModel().emptyLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setOnClickListener(new MyOnClickListener(sEmptyLayout));
            layout.setVisibility(View.GONE);
            layoutMap.put(sEmptyLayout, layout);
        }

        /**加载出错 布局*/
        if (view.getStatusLayoutModel().failLayoutId != Status.NO_LAYOUT_RES_ID) {
            View layout = LayoutInflater.from(context).inflate(view.getStatusLayoutModel().failLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setOnClickListener(new MyOnClickListener(sFailLayout));
            context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, new TypedValue(), true);
            layout.setVisibility(View.GONE);
            layoutMap.put(sFailLayout, layout);
        }
        bodyParentLayout.addView(bodyFrameLayout, index, bodyParentParams);
        bodyParentLayout.invalidate();
        return;
    }

    /**
     * 展示出错消息
     */
    public void setFailMessage(@NonNull String message) {
        TextView textView = (TextView) layoutMap.get(sFailLayout).findViewById(view.getStatusLayoutModel().failTextViewId);
        if ((textView == null) || TextUtils.isEmpty(message)) {
            LogUtil.e("出错信息为空");
            return;
        }
        textView.setText(message);
    }

    /**
     * 展示默认布局
     */
    public void showDefaultLayout() {
        switchLayout(sDefaultLayout);
    }

    /**
     * 展示延时对话框
     */
    public void showLoadingLayout() {
        switchLayout(sLoadingLayout);
    }

    /**
     * 展示请求成功后的布局
     */
    public void showSuccessLayout() {
        switchLayout(sSuccessLayout);
    }

    /**
     * 展示请求空数据的布局
     */
    public void showEmptyLayout() {
        switchLayout(sEmptyLayout);
    }

    /**
     * 给文本控件设置文本
     */
    public void setText(@NonNull View view, @NonNull String text) {
        if (view == null) {
            LogUtil.e("view 为空 ");
            return;
        }
        if (text == null) {
            LogUtil.e("文本 为空");
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else {
            LogUtil.e("view 不能被强转成 TextView  或 Button 或 EditText");
        }
        if (view instanceof EditText) {
            ((EditText) view).setSelection(text.length());
        }

    }

    /**
     * 给文本控件设置文本
     */
    public void setText(@IdRes int id, @NonNull String text) {
        View view = this.view.findView(id);
        if (view instanceof TextView) {
            TextView textView = (TextView) this.view;
            setText(textView, text);
        }
    }

    /**
     * 展示加载失败的布局
     */
    public void showFailLayout() {
        switchLayout(sFailLayout);
    }

    private void switchLayout(@NonNull String layoutName) {
        if (layoutMap == null) {
            return;
        }
        Set<Map.Entry<String, View>> entrySet = layoutMap.entrySet();
        for (Iterator<Map.Entry<String, View>> ir = entrySet.iterator(); ir.hasNext(); ) {
            Map.Entry<String, View> mapEntry = ir.next();
            String key = mapEntry.getKey();

            View view = mapEntry.getValue();
            if (layoutName.equals(key)) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }

        }
        if (sLoadingLayout.equals(layoutName)) {
            layoutMap.get(sDefaultLayout).setVisibility(View.VISIBLE);
        }
    }

    private final class MyOnClickListener implements View.OnClickListener {
        private String layoutName;

        public MyOnClickListener(String layoutName) {
            this.layoutName = layoutName;
        }

        @Override
        public void onClick(View view) {
            if (sFailLayout.equals(layoutName)) {
                ViewHelper.this.view.onStatusLayoutClick(Status.FAIL);
            } else if (sEmptyLayout.equals(layoutName)) {
                ViewHelper.this.view.onStatusLayoutClick(Status.EMPTY);
            }
        }
    }

    /**
     * 展示吐司, 默认居中偏上
     *
     * @param text 吐司内容
     */
    public void toast(@NonNull Context context, @NonNull String text) {
        if (context == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -100);
        }
        if (toastTextView == null) {
            toastTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.alex_toast_meizu, null);
        }
        if (!TextUtils.isEmpty(text)) {
            toastTextView.setText(text);
        } else {
            toastTextView.setText("  ");
        }
        toast.setView(toastTextView);
        toast.show();
    }

    /**
     * 取消吐司
     */
    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 销毁资源，防止内存泄漏
     */
    public void detachFromView() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        if (toastTextView != null) {
            toastTextView.destroyDrawingCache();
            toastTextView = null;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

        if (layoutMap != null) {
            Set<Map.Entry<String, View>> entrySet = layoutMap.entrySet();
            for (Iterator<Map.Entry<String, View>> ir = entrySet.iterator(); ir.hasNext(); ) {
                Map.Entry<String, View> mapEntry = ir.next();
                View view = mapEntry.getValue();
                if (view != null) {
                    view.destroyDrawingCache();
                    view = null;
                }
            }
            layoutMap.clear();
            layoutMap = null;
        }
        view = null;
    }

    /**
     * 将EditText的光标移至最后
     *
     * @param view
     */
    public void setSelection(@NonNull View view) {
        if (view == null) {
            LogUtil.e("View 为空");
            return;
        }
        if (!(view instanceof EditText)) {
            LogUtil.e("View 不能被强转成 EditText");
            return;
        }
        EditText editText = (EditText) view;
        Editable text = editText.getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        editText.setSelection(text.length());
    }

    /**
     * 数据转换: dp---->px
     */
    public static float dp2Px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
