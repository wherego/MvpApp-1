package com.alex.mvpapp.ui.userman;

import android.support.annotation.NonNull;

import org.alex.mvp.BaseHttpContract;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public interface LoginContract {

    interface View extends BaseHttpContract.View {
        /**
         * 登录时，本地验证手机号码不对
         */
        void onLocalValidateError(String message);

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 本地验证登录信息
         */
        void localValidateLoginInfo(@NonNull String phone, @NonNull String pwd);

        /**
         * 请求登录接口
         */
        void login(@NonNull String phone, @NonNull String pwd);

    }
}
