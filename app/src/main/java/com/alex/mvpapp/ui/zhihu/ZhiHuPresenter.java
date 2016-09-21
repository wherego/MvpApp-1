package com.alex.mvpapp.ui.zhihu;

import com.alex.mvpapp.httpman.UrlMan;
import com.alex.mvpapp.model.zhihu.NewsListBean;
import com.alibaba.fastjson.JSON;

import org.alex.mvp.CancelablePresenter;
import org.alex.retrofit.RetrofitClient;
import org.alex.rxjava.StringSubscriber;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class ZhiHuPresenter extends CancelablePresenter<ZhiHuContract.View> implements ZhiHuContract.Presenter {


    public ZhiHuPresenter(ZhiHuContract.View view) {
        super(view);
    }

    @Override
    public void loadNewsList(String loadType, int index) {
        this.loadType = loadType;
        String theme = "9";
        if (0 == index) {
            theme = "9";
        } else if (1 == index) {
            theme = "10";
        } else if (2 == index) {
            theme = "11";
        } else if (3 == index) {
            theme = "8";
        }

        new RetrofitClient.Builder()
                .baseUrl(UrlMan.ZhiHu.baseUrl)
                .build()
                .get(UrlMan.ZhiHu.articleList + theme)
                .lift(new ReplaceSubscriberOperator())
                .subscribe(new MyBaseSubscriber());
    }

    private final class MyBaseSubscriber extends StringSubscriber {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        /**
         * 网络请求失败
         *
         * @param message 请求失败的消息
         */
        @Override
        public void onError(String message) {
            view.toast(message);
            view.dismissLoadingDialog();
            view.onRefreshFinish();
        }

        /**
         * 网络请求成功
         *
         * @param result 网络请求的结果
         */
        @Override
        public void onSuccess(String result) {
            NewsListBean newsListBean = JSON.parseObject(result, NewsListBean.class);
            view.dismissLoadingDialog();
            view.onShowNewsList(loadType, newsListBean);
        }
    }
}
