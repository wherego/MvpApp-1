package org.alex.adapter.core;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
@SuppressWarnings("all")
public interface IBaseViewHolder<VH> {
    /**
     * 通过id获取控件
     *
     * @param id
     * @return
     */
    <T extends View> T findView(@IdRes int id);

    /**
     * 得到ConvertView
     */
    View getConvertView();

    /**
     * 给TextView 设置文本
     */
    VH setText(@IdRes int id, String text);

    /**
     * 设置点击事件
     */
    VH setOnClickListener(int position, @IdRes int... id);

    /**
     * 设置点击事件
     */
    VH setOnClickListener(int position, @IdRes int id);

    /**
     * 设置可见性
     */
    VH setVisibility(@IdRes int id, int visibility);

    /**
     * 得到标签
     */
    <T> T getTag(@IdRes int id);

    /**
     * 得到标签
     */
    <T> T getTag(@IdRes int id, int key);

    /**
     * 设置标签
     */
    VH setTag(@IdRes int id, Object tag);

    /**
     * 设置标签
     */
    VH setTag(@IdRes int id, int key, Object tag);
}
