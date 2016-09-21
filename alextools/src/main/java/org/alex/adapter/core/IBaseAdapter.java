package org.alex.adapter.core;

import android.content.Intent;
import android.support.annotation.NonNull;

import org.alex.model.ParcelableMap;

import java.util.List;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:51
 * 简述：
 */

@SuppressWarnings("all")
public interface IBaseAdapter<T> {

    /**
     * 获取数据集合
     */
    public List<T> getList();

    /**
     * 在List最后一条进行追加
     */
    public void addItem(T bean);

    /**
     * 在List最后一条进行追加
     */
    public void addItem(T bean, int position);

    /**
     * 在List最后一条进行追加
     */
    public void addItem(T... bean);

    /**
     * 在List最后一条进行追加
     */
    public void addItem(List<T> list);

    /**
     * 在List的position之后进行追加
     */
    public void addItem(List<T> list, int position);

    /**
     * 清空 并 刷新 list
     */
    public void refreshItem(List<T> list);

    /**
     * 清空 并 刷新 list
     */
    public void refreshItem(T bean);

    /**
     * 清空 并 刷新 list
     */
    public void refreshItem(T... bean);

    /**
     * 更新一条数据
     */
    public void updateItem(T bean, int position);

    /**
     * 移除一条数据
     *
     * @param position
     */
    public void removeItem(int position);

    /**
     * 移除一条数据
     */
    public void removeItem(T bean);

    /**
     * 移除多条数据
     */
    public void removeItem(List<T> list);


    /**
     * 清空数据
     */
    public void clearItem();

    /**
     * 页面跳转
     */
    public void startActivity(@NonNull Intent intent);

    /**
     * 页面跳转
     */
    public void startActivity(@NonNull Class clazz);

    /**
     * 页面跳转
     */
    public <M extends ParcelableMap> void startActivity(@NonNull Class clazz, M parcelableMap);
}

