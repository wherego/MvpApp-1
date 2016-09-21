package org.alex.retrofit.util;

import android.util.Log;

import java.net.URLEncoder;
import java.util.Map;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")

public class ReUtil {

    /**
     * 编码
     */
    public static String encode(String strUnCode) {
        try {
            return URLEncoder.encode(strUnCode, "UTF-8");
        } catch (Exception e) {
            Log.e("Url", "有异常：" + e);
        }
        return strUnCode;
    }

    public static final boolean isMapEmpty(Map map) {
        if (map == null || map.size() <= 0) {
            return true;
        }
        return false;
    }
    public static final boolean isMapNotEmpty(Map map) {
        if (map != null && map.size() > 0) {
            return true;
        }
        return false;
    }
}
