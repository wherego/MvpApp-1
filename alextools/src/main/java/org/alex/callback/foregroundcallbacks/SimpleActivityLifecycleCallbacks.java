package org.alex.callback.foregroundcallbacks;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 作者：Alex
 * 时间：2016年09月03日    14:26
 * 简述：
 */

@SuppressWarnings("all")
public abstract class SimpleActivityLifecycleCallbacks  implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState){

    }

    @Override
    public void onActivityStarted(Activity activity){

    }

    @Override
    public void onActivityResumed(Activity activity){

    }

    @Override
    public void onActivityPaused(Activity activity)
    {

    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {

    }

}