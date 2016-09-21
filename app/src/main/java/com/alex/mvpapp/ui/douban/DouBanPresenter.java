package com.alex.mvpapp.ui.douban;


import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.httpman.UrlMan;
import com.alex.mvpapp.model.MovieListBean;
import com.alibaba.fastjson.JSON;

import org.alex.mvp.CancelablePresenter;
import org.alex.retrofit.RetrofitClient;
import org.alex.rxjava.StringSubscriber;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
@SuppressWarnings("all")
public class DouBanPresenter extends CancelablePresenter<DouBanContract.View> implements DouBanContract.Presenter {
    private String loadType;
    private int start;
    private int count;

    public DouBanPresenter(DouBanContract.View view) {
        super(view);
        start = 0;
        count = 10;
    }

    /**
     * 加载 movie 列表
     */
    @Override
    public void loadMovieList(String loadType) {
        this.loadType = loadType;
        if ((AppCon.loadFirst.equals(loadType) || (AppCon.loadRefresh.equals(loadType)))) {
            start = 0;
            count = 10;
        } else {
            start += count;
            count = 10;
        }
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("start", start + "");
        paramsMap.put("count", count + "");
        new RetrofitClient.Builder()
                .baseUrl(UrlMan.DouBan.baseUrl)
                .build()
                .get(UrlMan.DouBan.movieList, paramsMap)
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
        }

        /**
         * 网络请求成功
         *
         * @param result 网络请求的结果
         */
        @Override
        public void onSuccess(String result) {
            MovieListBean movieListBean = JSON.parseObject(result, MovieListBean.class);
            view.onShowMovieList(loadType, movieListBean);
            view.dismissLoadingDialog();
        }
    }
}
