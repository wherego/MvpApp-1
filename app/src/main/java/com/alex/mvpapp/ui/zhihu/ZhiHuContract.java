package com.alex.mvpapp.ui.zhihu;

import org.alex.mvp.BaseHttpContract;
import com.alex.mvpapp.model.zhihu.NewsListBean;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public interface ZhiHuContract {

    interface View extends BaseHttpContract.View {

        /**
         * 展示 movie 列表
         */
        void onShowNewsList(String loadType, NewsListBean bean);

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 本地验证登录信息
         */
        void loadNewsList(String loadType, int index);

    }
}
