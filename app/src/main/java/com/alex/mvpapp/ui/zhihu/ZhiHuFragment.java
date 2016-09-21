package com.alex.mvpapp.ui.zhihu;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.BaseFragment;
import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.model.zhihu.NewsListBean;

import org.alex.callback.OnFragmentSelectedListener;
import org.alex.callback.refreshloadmore.OnLoadMoreListener;
import org.liaoinstan.springlayout.SpringLayout;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class ZhiHuFragment extends BaseFragment<ZhiHuPresenter> implements ZhiHuContract.View {

    private SpringLayout refreshLayout;
    private ZhiHuNewsListAdapter adapter;

    /**
     * 创建 Presenter
     */
    @Override
    protected ZhiHuPresenter createPresenter() {
        return new ZhiHuPresenter(this);
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_zhihu;
    }

    public static ZhiHuFragment getInstance(int index) {
        ZhiHuFragment fragment = new ZhiHuFragment();
        fragment.index = index;
        return fragment;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findViewById 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        adapter = new ZhiHuNewsListAdapter(activity);
        RecyclerView recyclerView = findView(R.id.rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout = findView(R.id.rl);
        refreshLayout.setOnLoadMoreListener(new MyOnRefreshLoadMoreToLoadListener());
        if (index != 0) {
            return;
        }
        if (canLoad) {
            canLoad = false;
            loadJsonData(AppCon.loadFirst);
        }
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
        if (presenter == null) {
            return;
        }
        presenter.loadNewsList(loadType, index);
    }

    /**
     * 展示 movie 列表
     *
     * @param loadType
     * @param bean
     */
    @Override
    public void onShowNewsList(String loadType, NewsListBean bean) {
        if (AppCon.loadFirst.equals(loadType)) {
            adapter.refreshItem(bean.stories);
        } else if (AppCon.loadRefresh.equals(loadType)) {
            adapter.refreshItem(bean.stories);
        } else if (AppCon.loadMore.equals(loadType)) {
            adapter.addItem(bean.stories);
        }
        refreshLayout.stopRefreshLayout();
    }

    /**
     * 下拉刷新 或 加载 完成
     */
    @Override
    public void onRefreshFinish() {
        super.onRefreshFinish();
        refreshLayout.stopRefreshLayout();
    }

    public final class MyOnFragmentSelectedListener implements OnFragmentSelectedListener {
        /**
         * 被选中的 Fragment的下标
         *
         * @param indexSelected
         */
        @Override
        public void onFragmentSelected(int indexSelected, Object extra) {
            if (index != indexSelected) {
                return;
            }
            if (canLoad) {
                canLoad = false;
                loadJsonData(AppCon.loadFirst);
            }
        }
    }
}
