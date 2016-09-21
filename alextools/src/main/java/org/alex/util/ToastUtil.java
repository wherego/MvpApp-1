package org.alex.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import org.alex.view.MeiZuToast;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:54
 * 简述：
 */

@SuppressWarnings("all")
public class ToastUtil {
    private static int gravityNone = -100;

    public static void shortNormal(String text) {
        show(gravityNone, Toast.LENGTH_SHORT, text);
    }

    public static void shortCenter(String text) {
        show(Gravity.CENTER, Toast.LENGTH_SHORT, text);
    }

    public static void shortTop(String text) {
        show(Gravity.TOP, Toast.LENGTH_SHORT, text);
    }

    public static void shortTopInThread(final Context context, final String text) {
        if (context == null) {
            return;
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                show(Gravity.TOP, Toast.LENGTH_SHORT, text);
            }
        });
    }

    public static void longNormal(String text) {
        show(gravityNone, Toast.LENGTH_LONG, text);
    }

    public static void longCenter(String text) {
        show(Gravity.CENTER, Toast.LENGTH_LONG, text);
    }

    public static void longTop(String text) {
        show(Gravity.TOP, Toast.LENGTH_LONG, text);
    }

    public static void longTopInThread(final Context context, final String text) {
        if (context == null) {
            return;
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                show(Gravity.CENTER, Toast.LENGTH_LONG, text);
            }
        });
    }

    public static void shortInThread(final Context context, final String text) {
        if (context == null) {
            return;
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                show(gravityNone, Toast.LENGTH_SHORT, text);
            }
        });
    }

    public static void shortCenterInThread(final Context context, final String text) {
        if (context == null) {
            return;
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                show(Gravity.CENTER, Toast.LENGTH_SHORT, text);
            }
        });
    }

    public static void longInThread(final Context context, final String text) {
        if (context == null) {
            return;
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                show(gravityNone, Toast.LENGTH_LONG, text);
            }
        });
    }

    public static void longCenterInThread(final Context context, final String text) {
        if (context == null) {
            return;
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                show(Gravity.CENTER, Toast.LENGTH_LONG, text);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static void show(int gravity, int duration, String text) {
        MeiZuToast meiZuToast = MeiZuToast.makeText(BaseUtil.app(), text, duration);
        if (gravity == Gravity.CENTER) {
            meiZuToast.setGravity(gravity, 0, -100);
        } else if (gravity == Gravity.TOP) {
            meiZuToast.setGravity(gravity, 0, 100);
        }
        meiZuToast.setText(text);
        meiZuToast.show();
    }

}

