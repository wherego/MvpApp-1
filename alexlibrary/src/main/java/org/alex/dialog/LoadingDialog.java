package org.alex.dialog;

import android.content.Context;
import android.view.View;

import org.alex.alexlibrary.R;
import org.alex.dialog.base.SimpleDialog;

/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class LoadingDialog extends SimpleDialog<LoadingDialog> {

    /*super(context,R.style.alex_dialog_base_light_style);*/
    public LoadingDialog(Context context) {
        super(context);
    }

    /**
     * 配置 对话框的 布局文件
     */
    @Override
    public int getLayoutRes() {
        return R.layout.alex_dialog_loading;
    }

    /**
     * 在 这里 进行 finndView  设置点击事件
     */
    @Override
    public void onCreateDialog() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {

    }
}
