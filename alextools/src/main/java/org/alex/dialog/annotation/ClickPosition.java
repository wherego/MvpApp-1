package org.alex.dialog.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：Alex
 * <br/>
 * 时间：2016/9/5 17:59
 * <br/>
 * 简述：
 */
@SuppressWarnings("all")
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
@Inherited
@StringDef(value = {
        ClickPosition.LEFT,
        ClickPosition.TOP,
        ClickPosition.RIGHT,
        ClickPosition.BOTTOM,
        ClickPosition.CENTER,
        ClickPosition.SUBMIT,
        ClickPosition.CANCEL,
        ClickPosition.OPEN,
        ClickPosition.CLOSE,
        ClickPosition.AGREE,
        ClickPosition.REFUSE,
        ClickPosition.ACCEPT,
        ClickPosition.UPDATE,
        ClickPosition.DELETE,
        ClickPosition.ALTER,
        ClickPosition.FORGET,
        ClickPosition.IGNORE,
        ClickPosition.STOP,
        ClickPosition.CLEAR,
        ClickPosition.FINISH,
        ClickPosition.REFRESH,
        ClickPosition.START,
        ClickPosition.SEND
})

public @interface ClickPosition {
    /**
     * 左
     */
    public static final String LEFT = "LEFT";

    /**
     * 顶部
     */
    public static final String TOP = "TOP";
    /**
     * 右
     */
    public static final String RIGHT = "RIGHT";
    /**
     * 底部
     */
    public static final String BOTTOM = "BOTTOM";
    /**
     * 中间
     */
    public static final String CENTER = "CENTER";
    /**
     * 确定
     */
    public static final String SUBMIT = "SUBMIT";
    /**
     * 取消
     */
    public static final String CANCEL = "CANCEL";

    /**
     * 关闭
     */
    public static final String CLOSE = "CLOSE";
    /**
     * 打开
     */
    public static final String OPEN = "OPEN";
    /**
     * 同意
     */
    public static final String AGREE = "AGREE";
    /**
     * 拒绝
     */
    public static final String REFUSE = "REFUSE";
    /**
     * 接受
     */
    public static final String ACCEPT = "ACCEPT";
    /**
     * 升级
     */
    public static final String UPDATE = "UPDATE";
    /**
     * 删除
     */
    public static final String DELETE = "DELETE";
    /**
     * 修改
     */
    public static final String ALTER = "ALTER";
    /**
     * 忘记
     */
    public static final String FORGET = "FORGET";
    /**
     * 忽略
     */
    public static final String IGNORE = "IGNORE";
    /**
     * 开始
     */
    public static final String START = "START";
    /**
     * 停止
     */
    public static final String STOP = "STOP";
    /**
     * 完成
     */
    public static final String FINISH = "FINISH";
    /**
     * 清理
     */
    public static final String CLEAR = "CLEAR";
    /**
     * 刷新
     */
    public static final String REFRESH = "REFRESH";
    /**
     * 发送
     */
    public static final String SEND = "SEND";
}
