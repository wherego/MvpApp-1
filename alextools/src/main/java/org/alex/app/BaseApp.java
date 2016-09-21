package org.alex.app;

import android.app.Application;

import org.alex.helper.CrashHandler;
import org.alex.util.BaseUtil;
import org.alex.util.LogUtil;

/**
 * 作者：Alex
 * 时间：2016年09月03日    14:07
 * 简述：
 */

@SuppressWarnings("all")
public class BaseApp extends Application {
    protected static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BaseUtil.getInstance().app(this).onCrashListener(new MyOnCrashListener());
    }

    public static Application getApp() {
        return instance;
    }

    private final class MyOnCrashListener implements CrashHandler.OnCrashListener {
        @Override
        public void onCrash(String error) {
            LogUtil.e(error);
        }
    }

}

