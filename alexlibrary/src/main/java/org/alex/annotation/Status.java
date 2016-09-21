package org.alex.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
@Inherited
@IntDef(value = {Status.DEFAULT, Status.LOADING, Status.SUCCESS, Status.FAIL, Status.EMPTY, Status.NET_ERROR})
public @interface Status {
    /**
     * 默认
     */
    public static final int DEFAULT = 1000;
    /**
     * 主布局
     */
    public static final int LOADING = 1001;
    /**
     * 主布局
     */
    public static final int SUCCESS = 1002;
    /**
     * 失败布局
     */
    public static final int FAIL = 1003;
    /**
     * 空数据布局
     */
    public static final int NET_ERROR = 1004;
    /**
     * 空数据布局
     */
    public static final int EMPTY = 1005;

    /**
     * 布局文件不存在
     */
    public static final int NO_LAYOUT_RES_ID = -1;
    /**
     * 控件资源id不存在
     */
    public static final int NO_RES_ID = -1;
}
