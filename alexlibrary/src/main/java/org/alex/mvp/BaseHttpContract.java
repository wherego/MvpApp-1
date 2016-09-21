package org.alex.mvp;

import android.support.annotation.NonNull;

import org.alex.model.StatusLayoutModel;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public interface BaseHttpContract {

    interface View extends BaseContract.View {

        /**
         * 初始化延时对话框
         */
        void initLoadingDialog();

        /**
         * 展示延时对话框
         */
        void showLoadingDialog();

        /**
         * 隐藏延时对话框
         */
        void dismissLoadingDialog();

        /**
         * 得到多状态布局， 数据模型
         */
        StatusLayoutModel getStatusLayoutModel();

        /**
         * 展示默认布局
         */
        void showDefaultLayout();

        /**
         * 展示loading布局
         */
        void showLoadingLayout();

        /**
         * 展示请求成功后的布局
         */
        void showSuccessLayout();

        /**
         * 展示请求空数据的布局
         */
        void showEmptyLayout();

        /**
         * 展示出错消息
         */
        void setFailMessage(@NonNull String message);

        /**
         * 展示加载失败的布局
         */
        void showFailLayout();

        /**
         * 下拉刷新 或 加载 完成
         */
        void onRefreshFinish();
    }

    interface Presenter extends BaseContract.Presenter {

    }
}
