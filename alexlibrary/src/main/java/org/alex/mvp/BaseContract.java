package org.alex.mvp;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import org.alex.annotation.Status;
import org.alex.model.ParcelableMap;

import rx.Subscription;

/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public interface BaseContract {
    interface View {
        /**
         * 配置 布局文件的 资源 id
         */
        @LayoutRes
        int getLayoutResId();

        /**
         * 获取bodyView的 资源id
         */
        @IdRes
        int getBodyViewId();

        /**
         * 执行在 onCreateView 中
         * 通过 findView 初始主视图化控件
         * 初始化所有基础数据，
         */
        void onCreateData();

        /**
         * 展示吐司
         *
         * @param text 吐司内容
         */
        void toast(@NonNull String text);

        /**
         * 多状态布局的 点击事件
         */
        void onStatusLayoutClick(@Status int status);

        /**
         * 给文本控件设置文本
         */
        void setText(@NonNull android.view.View view, @NonNull String text);

        /**
         * 给文本控件设置文本
         */
        void setText(@IdRes int id, @NonNull String text);

        /**
         * 将EditText的光标移至最后
         */
        void setSelection(@NonNull android.view.View view);

        /**
         * 得到上下文对象
         */
        @NonNull
        Context getViewContext();

        /**
         * 扩展的findViewById
         */
        <T extends android.view.View> T findView(@IdRes int id);

        /**
         * 给控件添加点击事件
         */
        void setOnClickListener(@IdRes int... id);

        /**
         * 页面跳转，无传值
         */
        void startActivity(@NonNull Class clazz);

        /**
         * 页面跳转，有传值
         */
        <M extends ParcelableMap> void startActivity(@NonNull Class clazz, @NonNull M parcelableMap);


        /**
         * 添加 Subscription， 管理订阅事件
         */
        void addSubscription(@NonNull Subscription subscription);

        /**
         * 销毁资源，防止内存泄漏
         */
        void onDetachFromView();

    }

    interface Presenter {


    }

}
