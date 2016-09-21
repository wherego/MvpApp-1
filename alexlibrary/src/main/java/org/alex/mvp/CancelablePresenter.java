package org.alex.mvp;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class CancelablePresenter<V extends BaseContract.View> {
    protected V view;
    private CompositeSubscription compositeSubscription;
    private Subscription subscription;
    /**
     * 加载类型
     */
    protected String loadType;

    public CancelablePresenter(@NonNull V view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 添加 Subscription
     */
    public void addSubscription(Subscription subscription) {
        compositeSubscription = (compositeSubscription == null) ? new CompositeSubscription() : compositeSubscription;
        compositeSubscription.add(subscription);
    }


    /**
     * 取消订阅事件， 防止内存泄漏
     */
    public void detachFromView() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = null;
        compositeSubscription = null;
        view = null;
    }

    public final class AddSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            CancelablePresenter.this.addSubscription(subscriber);
            return subscriber;
        }
    }

    public final class ReplaceSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            CancelablePresenter.this.subscription = subscriber;
            return subscriber;
        }
    }
}
