package com.alex.mvpapp.dialog;

import android.content.Context;
import android.view.View;

import com.alex.mvpapp.R;

import org.alex.dialog.annotation.ClickPosition;
import org.alex.dialog.base.Top2BottomDialog;

/**
 * 作者：Alex
 * 时间：2016年09月10日    17:40
 * 简述：
 */

public class NoticeDialog extends Top2BottomDialog<NoticeDialog> {
    public NoticeDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_notice;
    }

    @Override
    public void onCreateDialog() {
        setOnCilckListener(R.id.tv_submit,R.id.tv_cancel);
    }

    @Override
    public void onClick(View v) {
        if(R.id.tv_submit == v.getId()){
           onDialogClickListener(ClickPosition.SUBMIT);
        }else  if(R.id.tv_cancel == v.getId()){
            onDialogClickListener(ClickPosition.CANCEL);
        }
    }
}
