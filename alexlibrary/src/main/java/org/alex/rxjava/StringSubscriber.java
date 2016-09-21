package org.alex.rxjava;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public abstract class StringSubscriber extends BaseSubscriber<String> {
    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    @Override
    public abstract void onSuccess(String result);
}
