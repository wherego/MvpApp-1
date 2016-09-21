package org.alex.rxjava;


import org.alex.util.HttpErrorUtil;
import org.alex.util.LogUtil;

import rx.Subscriber;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    public BaseSubscriber() {

    }

    public BaseSubscriber(Object tag) {
        this.tag = tag;
    }

    /**
     * 开始网络请求
     */
    public abstract void onStart();

    @Override
    public void onError(Throwable e) {
        LogUtil.w("有异常：" + e);
        onError(HttpErrorUtil.getMessage(e));
    }

    /**
     * 网络请求失败
     *
     * @param message 请求失败的消息
     */
    public abstract void onError(String message);

    @Override
    public void onCompleted() {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(T result);

}
