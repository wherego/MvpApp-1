package org.alex.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:07
 * 简述：
 */

@SuppressWarnings("all")
public class ActivityUtil {
    private static ArrayList<Activity> activityList;

    private ActivityUtil() {
        ActivityUtil.activityList = new ArrayList<>();
    }

    /**
     * 供 onActivityCreated 调用
     */
    public static void addActivity(Activity activity) {
        activityList = (activityList == null) ? new ArrayList<Activity>() : activityList;
        if (activity != null) {
            activityList.add(activity);
        }
    }

    /**
     * 供  onActivityDestroyed 调用
     */
    public static void removeActivity(Activity activity) {
        for (int i = 0; (activityList != null) && (i < activityList.size()); i++) {
            if (activityList.get(i).getClass().getSimpleName().equals(activity.getClass().getSimpleName())) {
                activityList.remove(i);
                break;
            }
        }
    }

    public static void finishActivity(String activityName) {
        for (int i = 0; (activityList != null) && (!TextUtils.isEmpty(activityName)); i++) {
            Activity activity = activityList.get(i);
            if (activity.getClass().getSimpleName().equals(activityName)) {
                activity.finish();
                activity = null;
                break;
            }
        }
    }

    public static void finishActivity(String... activityName) {
        for (int i = 0; (activityName != null) && (i < activityName.length); i++) {
            finishActivity(activityName[i]);
        }
    }

    /**
     * 清除 activityName 之上 的 所有Activity， activityName 保留状态
     *
     * @param activityName Activity 的名字 Activity.getClass().getSimpleName()
     */
    public static void finishActivityAbove(String activityName) {
        if ((activityName == null) || (activityList == null) || (activityList.size() <= 0)) {
            return;
        }
        for (int i = (activityList.size() - 1); i >= 0; i--) {
            if (!activityName.equals(activityList.get(i).getClass().getSimpleName())) {
                activityList.get(i).finish();
            } else {
                break;
            }
        }
    }

    /**
     * 杀死 所有Activity
     */
    public static void clearActivityList() {
        if ((activityList == null) || (activityList.size() <= 0)) {
            activityList = null;
            return;
        }
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            activity.finish();
            activityList.remove(i);
            activity = null;
        }
        activityList.clear();
        activityList = null;
    }

    /**
     * 获取 App 的任务栈中  所有的Activity的 名字
     */
    public static List<String> getActivityNameList() {
        List<String> nameList = new ArrayList<String>();
        for (int i = 0; (activityList != null) && (i < activityList.size()); i++) {
            String name = activityList.get(i).getClass().getSimpleName();
            nameList.add(name);
        }
        return nameList;
    }

    /**
     * 判断 Activity 是否在 任务栈中
     */
    public static boolean isActivityInTask(@NonNull String activityName) {
        if ((activityList == null) || TextUtils.isEmpty(activityName)) {
            return false;
        }
        for (int i = 0; (activityList != null) && (i < activityList.size()); i++) {
            String name = activityList.get(i).getClass().getSimpleName();
            if (name.equals(activityName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 Activity 是否处于 站定
     */
    public static boolean isActivityInTop(@NonNull String activityName) {
        if ((activityList == null) || TextUtils.isEmpty(activityName)) {
            return false;
        }
        String name = activityList.get(activityList.size() - 1).getClass().getSimpleName();
        if (name.equals(activityName)) {
            return true;
        }
        return false;
    }

    /**
     * 根据 名字 获取 Activity对象
     */
    @Nullable
    public static Activity getActivity(@NonNull String activityName) {
        if ((activityList == null) || TextUtils.isEmpty(activityName)) {
            return null;
        }
        for (int i = 0; (activityList != null) && (i < activityList.size()); i++) {
            String name = activityList.get(i).getClass().getSimpleName();
            if (name.equals(activityName)) {
                return activityList.get(i);
            }
        }
        return null;
    }

    /**
     * 获取栈顶的 Activity
     */
    @Nullable
    public static Activity getTopActivity() {
        if ((activityList == null) || (activityList.size() <= 0)) {
            return null;
        }
        return activityList.get(activityList.size() - 1);
    }

    /**
     * 获取栈顶的 Activity的名字
     */
    @Nullable
    public static String getTopActivityName() {
        if ((activityList == null) || (activityList.size() <= 0)) {
            return null;
        }
        return activityList.get(activityList.size() - 1).getClass().getSimpleName();
    }
}

