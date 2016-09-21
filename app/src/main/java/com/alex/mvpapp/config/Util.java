package com.alex.mvpapp.config;

import com.alex.mvpapp.model.UserBean;
import org.alex.util.LogUtil;

import java.util.Iterator;
import java.util.Map;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
public class Util {
    public static void printMap(Map map) {
        if ((map == null) || (map.size() <= 0)) {
            return;
        }
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair != null) {
                if (pair.getValue() instanceof UserBean) {
                    UserBean userBean = (UserBean) pair.getValue();
                    LogUtil.w("key = " + pair.getKey() + " : " + pair.getValue().getClass().getSimpleName() + " = " + userBean.phone + " " + userBean.pwd);
                } else {
                    LogUtil.w("key = " + pair.getKey() + " : " + pair.getValue().getClass().getSimpleName() + " = " + pair.getValue());
                }
            }
        }
    }
}
