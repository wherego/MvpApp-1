package com.alex.mvpapp.dialog;

import android.content.Context;
import android.view.View;

import com.alex.mvpapp.R;

import org.alex.dialog.annotation.ClickPosition;
import org.alex.dialog.base.CenterScaleDialog;
/**
 * 作者：Alex
 * 时间：2016年09月09日    23:52
 * 简述：
 */

public class OneButtonDialog extends CenterScaleDialog<OneButtonDialog> {

    public OneButtonDialog(Context context) {
        super(context);
    }

    /**
     * 配置 对话框的 布局文件
     */
    @Override
    public int getLayoutRes() {
        return R.layout.dialog_one_button;
    }

    /**
     * 在 这里 进行 findView  设置点击事件
     */
    @Override
    public void onCreateDialog() {
        setOnCilckListener(R.id.tv_submit);
    }

    /**
     * 处理按钮点击事件 并绑定 onDialogClickListener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (R.id.tv_submit == v.getId()) {
            if (onDialogClickListener != null) {
                onDialogClickListener.onDialogClick(this, ClickPosition.SUBMIT);
            }
        }
    }
}
