package org.alex.app;

import android.app.Activity;
import android.os.Bundle;

import org.alex.callback.foregroundcallbacks.SimpleActivityLifecycleCallbacks;
import org.alex.util.ActivityUtil;
import org.alex.util.LogUtil;

/**
 * 作者：Alex
 * 时间：2016年09月03日    22:43
 * 简述：
 */
public class LifecycleApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
          /*管理 Activity 任务栈*/
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }
    private final class MyActivityLifecycleCallbacks extends SimpleActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            super.onActivityCreated(activity, savedInstanceState);
            ActivityUtil.addActivity(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtil.w("即将移除 " + activity.getClass().getSimpleName());
            ActivityUtil.removeActivity(activity);
        }
    }

}
