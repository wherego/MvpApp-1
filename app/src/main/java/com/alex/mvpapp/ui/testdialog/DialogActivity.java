package com.alex.mvpapp.ui.testdialog;

import android.view.View;
import android.view.Window;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.SimpleActivity;
import com.alex.mvpapp.dialog.NoticeDialog;
import com.alex.mvpapp.dialog.OneButtonDialog;
import com.alex.mvpapp.dialog.SelectedProductDialog;

import org.alex.dialog.LoadingDialog;
import org.alex.dialog.annotation.ClickPosition;
import org.alex.dialog.base.SimpleDialog;
import org.alex.dialog.callback.OnDialogClickListener;
import org.alex.util.LogUtil;

/**
 * 作者：Alex
 * 时间：2016年09月09日    23:41
 * 简述：
 * {@link com.alex.mvpapp.ui.MainActivity}
 */

public class DialogActivity extends SimpleActivity {
    private LoadingDialog loadingDialog;
    private OneButtonDialog oneButtonDialog;
    private SelectedProductDialog selectedProductDialog;
    private NoticeDialog noticeDialog;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_dialog;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        MyOnDialogClickListener onDialogClickListener = new MyOnDialogClickListener();
        loadingDialog = new LoadingDialog(this);//.setOnKeyListener(SimpleOnKeyListener.dismissNotKillActivity(this));
        oneButtonDialog = new OneButtonDialog(this).setOnDialogClickListener(onDialogClickListener);
        selectedProductDialog = new SelectedProductDialog(this, findViewById(Window.ID_ANDROID_CONTENT)).setOnDialogClickListener(onDialogClickListener);
        noticeDialog = new NoticeDialog(this).tag("noticeDialog").setOnDialogClickListener(onDialogClickListener);
        setOnClickListener(R.id.tv_loadingDialog, R.id.tv_oneButtonDialog, R.id.tv_taobao, R.id.tv_notice);
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     * 不可以注释掉  super.onClick(v);
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_loadingDialog == v.getId()) {
            loadingDialog.show();
        } else if (R.id.tv_oneButtonDialog == v.getId()) {
            oneButtonDialog.show();
        } else if (R.id.tv_taobao == v.getId()) {
            selectedProductDialog.show();
        } else if (R.id.tv_notice == v.getId()) {
            noticeDialog.show();
        }
    }

    private final class MyOnDialogClickListener implements OnDialogClickListener<SimpleDialog> {

        @Override
        public void onDialogClick(SimpleDialog dialog, @ClickPosition String clickPosition) {
            LogUtil.e("tag = " + dialog.tag + " clickPosition =" + clickPosition);
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.destroy();
            loadingDialog = null;
        }
        if (oneButtonDialog != null) {
            oneButtonDialog.destroy();
            oneButtonDialog = null;
        }
        if (selectedProductDialog != null) {
            selectedProductDialog.destroy();
            selectedProductDialog = null;
        }
        if (noticeDialog != null) {
            noticeDialog.destroy();
            noticeDialog = null;
        }
    }
}
