package com.alex.mvpapp.ui.userman;

import android.view.View;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.BaseActivity;

import org.alex.checkbox.CheckBox;
import org.alex.iconinputlayout.IconInputLayout;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    private IconInputLayout iilPhone;
    private IconInputLayout iilPwd;
    private CheckBox checkBox;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        iilPhone = findView(R.id.iil_phone);
        iilPwd = findView(R.id.iil_pwd);
        checkBox = findView(R.id.cb);
        setText(R.id.tv_title, "登录");
        setOnClickListener(R.id.tv_remember, R.id.tv_login);
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
        if (R.id.tv_login == v.getId()) {
            presenter.localValidateLoginInfo(iilPhone.getText(), iilPwd.getText());
        } else if (R.id.tv_remember == v.getId()) {
            checkBox.toggleChecked(true);
        }
    }

    @Override
    public void onLocalValidateError(String message) {
        toast(message);
    }

}
