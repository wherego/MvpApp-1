package com.alex.mvpapp.ui.testfloatpagerlayout;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;

import com.alex.mvpapp.R;

import org.alex.callback.refreshloadmore.OnLoadMoreListener;
import org.alex.mvp.CancelablePresenter;
import org.alex.util.LogUtil;
import org.liaoinstan.springlayout.SpringLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016/9/13 11:00
 * 简述：
 * 启动者：
 * -----------{@link FloatPagerLayoutActivity}
 */
public class BeforeGameScoreFragment extends BaseBeforeGameFragment<CancelablePresenter> {
    private SpringLayout springLayout;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_before_score;
    }

    public static BeforeGameScoreFragment getInstance(int index) {
        BeforeGameScoreFragment fragment = new BeforeGameScoreFragment();
        fragment.index = index;
        return fragment;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        RecyclerView recyclerView = findView(R.id.rv);
        recyclerView.addOnScrollListener(new MyOnScrollListener());
        BeforeGameScoreAdapter adapter = new BeforeGameScoreAdapter(activity);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        adapter.addItem(list);
        recyclerView.setAdapter(adapter);
        springLayout = findView(R.id.sl);
        springLayout.setOnLoadMoreListener(new MyOnLoadMoreListener());
    }

    private final class MyOnLoadMoreListener implements OnLoadMoreListener {

        /**
         * 上拉加载，回调接口
         */
        @Override
        public void onLoadMore() {
            new LoadTadk().execute();
        }

        /**
         * 下拉刷新，回调接口
         */
        @Override
        public void onRefresh() {
            new LoadTadk().execute();
        }
    }

    private final class LoadTadk extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(1500);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            springLayout.stopRefreshLayout();
        }

    }

    private final class MyOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int offset = recyclerView.computeVerticalScrollOffset();
            final int range = recyclerView.computeVerticalScrollRange();
            int extent = recyclerView.computeVerticalScrollExtent();
            LogUtil.e("offset = " + offset + " range = " + range + " extent = " + extent);
            isChildOnTop = recyclerView.computeVerticalScrollOffset() == 0;
            if (floatPagerLayoutActivity.indexFragment == index) {
                floatPagerLayoutActivity.getFloatTitleLayout().setIsChildOnTop(isChildOnTop);
            }
        }
    }
}
