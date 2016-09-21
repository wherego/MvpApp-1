package org.alex.callback.foregroundcallbacks;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;

import org.alex.util.LogUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:58
 * 简述：
 */

@SuppressWarnings("all")
public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {

    public static final long CHECK_DELAY = 500;

    public interface AppStatusListener {

        void onBecameForeground();

        void onBecameBackground();

    }

    private static ForegroundCallbacks instance;

    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<AppStatusListener> listeners = new CopyOnWriteArrayList<AppStatusListener>();
    private Runnable checkRunnable;

    public static ForegroundCallbacks init(Application application) {
        if (instance == null) {
            instance = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(AppStatusListener listener) {
        listeners.add(listener);
    }

    public void removeListener(AppStatusListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;

        if (checkRunnable != null)
            handler.removeCallbacks(checkRunnable);

        if (wasBackground) {
            LogUtil.w("App 进入前台");
            for (AppStatusListener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    LogUtil.e("有异常："+exc);
                }
            }
        } else {
            LogUtil.w("App 保持在后台");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
        if (checkRunnable != null){
            handler.removeCallbacks(checkRunnable);
        }
        handler.postDelayed(checkRunnable = new Runnable() {
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    LogUtil.w("App 进入后台");
                    for (AppStatusListener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            LogUtil.e("有异常："+exc);
                        }
                    }
                } else {
                    LogUtil.w("App 保持在前台");
                }
            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}

