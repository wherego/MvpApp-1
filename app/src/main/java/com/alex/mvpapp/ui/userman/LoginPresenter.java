package com.alex.mvpapp.ui.userman;

import android.support.annotation.NonNull;

import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.httpman.UrlMan;
import com.alex.mvpapp.model.UserBean;
import com.alex.mvpapp.model.qianguan.LoginBean;
import com.alibaba.fastjson.JSON;

import org.alex.mvp.CancelablePresenter;
import org.alex.retrofit.RetrofitClient;
import org.alex.rxjava.StringSubscriber;
import org.alex.util.LogUtil;
import org.alex.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class LoginPresenter extends CancelablePresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void localValidateLoginInfo(String phone, String pwd) {
        if (!StringUtil.isPhoneNum(phone)) {
            view.onLocalValidateError(AppCon.userManPhone);
            return;
        }
        if (!StringUtil.isLengthOK(pwd, AppCon.loginPwdMinLength, AppCon.loginPwdMaxLength)) {
            view.onLocalValidateError(AppCon.userLoginPwd);
            return;
        }
        login(phone, pwd);
    }

    @Override
    public void login(@NonNull String phone, @NonNull String pwd) {
        HashMap<String, String> bodyParamsMap = new HashMap<>();
        bodyParamsMap.put("bPhone", phone);
        bodyParamsMap.put("bPwd", pwd);
        UserBean userBean = new UserBean();
        userBean.phone = phone;
        userBean.pwd = pwd;
        LinkedHashMap<String, String> queryParamsMap = new LinkedHashMap<>();
        queryParamsMap.put("qPhone", phone);
        queryParamsMap.put("qPwd", pwd);
        new RetrofitClient.Builder()
                .baseUrl(UrlMan.AlexApp.szBaseUrl)
                .build()
                .post(UrlMan.AlexApp.login, queryParamsMap, bodyParamsMap)
                .lift(new ReplaceSubscriberOperator())
                .subscribe(new MyStringSubscriber());
    }


    private final class MyStringSubscriber extends StringSubscriber {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        /**
         * 网络请求失败
         *
         * @param message 请求失败的消息
         */
        @Override
        public void onError(String message) {
            LogUtil.e(message);
            view.toast(message);
            view.dismissLoadingDialog();
        }

        @Override
        public void onSuccess(String result) {
            LoginBean loginBean = JSON.parseObject(result, LoginBean.class);
            view.toast("登录成功");
            view.dismissLoadingDialog();
        }
    }
}
