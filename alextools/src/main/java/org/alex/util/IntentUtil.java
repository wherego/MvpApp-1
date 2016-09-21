package org.alex.util;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * 作者：Alex
 * 时间：2016/9/6 09:51
 * 简述：
 */
@SuppressWarnings("all")
public class IntentUtil {

    /**
     * 选择浏览器
     *
     * @param url 跳转链接
     * @param tip 请选择浏览器
     */
    public static void start4Browser(String url, String tip) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        Intent chooser = Intent.createChooser(intent, tip).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (chooser.resolveActivity(BaseUtil.app().getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    /**
     * 选择浏览器
     *
     * @param url 跳转链接
     */
    public static void start4Browser(String url) {
        start4Browser(url, "请选择浏览器");
    }

    /**
     * 跳转到拨号器页面
     * @param phoneNum
     * 对方的手机号
     */
    public static void start4Dial(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }

    /**
     * 跳转到发送邮件
     *
     * @param email   收件人
     * @param content 邮件内容
     */
    public static void start4Email(String email[], String content) {
        start4Email(email, "", content, "请选择邮件类应用");
    }

    /**
     * 跳转到发送邮件
     *
     * @param email   收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public static void start4Email(String email[], String subject, String content) {
        start4Email(email, subject, content, "请选择邮件类应用");
    }

    /**
     * 跳转到发送邮件
     *
     * @param email   收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param tip     请选择邮件类应用
     */
    public static void start4Email(String email[], String subject, String content, String tip) {
        start4Email(email, null, subject, content, "请选择邮件类应用");
    }

    /**
     * 跳转到发送邮件
     *
     * @param email   收件人
     * @param emailCC 抄送
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param tip     请选择邮件类应用
     */
    public static void start4Email(String email[], String emailCC[], String subject, String content, String tip) {
            /* 需要注意，email必须以数组形式传入*/
        Intent intent = new Intent(Intent.ACTION_SEND);
        /* 设置邮件格式  */
        intent.setType("message/rfc822");
        /*接收人  */
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        if (emailCC != null) {
        /*抄送人 */
            intent.putExtra(Intent.EXTRA_CC, emailCC);
        }
        /*主题*/
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        Intent chooser = Intent.createChooser(intent, tip).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (chooser.resolveActivity(BaseUtil.app().getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private static void startActivity(@NonNull Intent intent) {
        if (intent == null) {
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseUtil.app().startActivity(intent);
    }

}
