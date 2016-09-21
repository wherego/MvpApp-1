package com.alex.mvpapp;

import com.squareup.leakcanary.LeakCanary;

import org.alex.app.BaseApp;
import org.alex.util.FontUtil;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        /*观察 内存泄漏*/
        LeakCanary.install(this);
        FontUtil.initFormAssets("font/simkai.ttf");
    }
}
