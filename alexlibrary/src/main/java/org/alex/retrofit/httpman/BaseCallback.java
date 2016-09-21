package org.alex.retrofit.httpman;

/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public abstract class BaseCallback<T> {
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    public BaseCallback() {

    }

    public BaseCallback(Object tag) {
        this.tag = tag;
    }

    /**
     * 开始网络请求
     */
    public abstract void onStart();


    /**
     * 网络请求失败
     *
     * @param message 请求失败的消息
     */
    public abstract void onError(String message);

    public void onCompleted() {
    }

    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(T result);
}
