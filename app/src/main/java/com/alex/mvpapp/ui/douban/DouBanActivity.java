package com.alex.mvpapp.ui.douban;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.BaseActivity;
import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.model.MovieListBean;

import org.alex.callback.refreshloadmore.OnLoadMoreListener;
import org.liaoinstan.springlayout.SpringLayout;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class DouBanActivity extends BaseActivity<DouBanPresenter> implements DouBanContract.View {
    private DouBanAdapter adapter;
    private SpringLayout refreshLayout;

    /**
     * 创建 Presenter
     */
    @Override
    protected DouBanPresenter createPresenter() {
        return new DouBanPresenter(this);
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_douban;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        setText(R.id.tv_title, "豆瓣电影");
        RecyclerView recyclerView = findView(R.id.rv);
        adapter = new DouBanAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout = findView(R.id.rl);
        refreshLayout.setOnLoadMoreListener(new MyOnRefreshLoadMoreToLoadListener());
        loadJsonData(AppCon.loadFirst);
    }

    private final class MyOnRefreshLoadMoreToLoadListener implements OnLoadMoreListener {
        @Override
        public void onRefresh() {
            loadJsonData(AppCon.loadRefresh);
        }

        @Override
        public void onLoadMore() {
            loadJsonData(AppCon.loadMore);
        }
    }

    private void loadJsonData(String loadType) {
        presenter.loadMovieList(loadType);
    }

    /**
     * 展示 movie 列表
     *
     * @param bean
     */
    @Override
    public void onShowMovieList(String loadType, MovieListBean bean) {
        if (AppCon.loadFirst.equals(loadType)) {
            adapter.refreshItem(bean.subjects);
        } else if (AppCon.loadRefresh.equals(loadType)) {
            adapter.refreshItem(bean.subjects);
        } else if (AppCon.loadMore.equals(loadType)) {
            adapter.addItem(bean.subjects);
        }
        refreshLayout.stopRefreshLayout();
    }
}
