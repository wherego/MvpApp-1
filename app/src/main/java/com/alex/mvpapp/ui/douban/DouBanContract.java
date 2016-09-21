package com.alex.mvpapp.ui.douban;

import com.alex.mvpapp.model.MovieListBean;
import org.alex.mvp.BaseHttpContract;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public interface DouBanContract {

    interface View extends BaseHttpContract.View {
        /**
         * 展示 movie 列表
         */
        void onShowMovieList(String loadType, MovieListBean bean);
    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 加载 movie 列表
         */
        void loadMovieList(String loadType);

    }
}
