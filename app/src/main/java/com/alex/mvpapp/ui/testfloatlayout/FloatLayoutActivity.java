package com.alex.mvpapp.ui.testfloatlayout;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.SimpleActivity;
import com.alex.mvpapp.ui.testfloatpagerlayout.BeforeGameScoreAdapter;

import org.alex.callback.refreshloadmore.OnLoadMoreListener;
import org.alex.floatindicatorlayout.FloatIndicatorLayout;
import org.alex.util.LogUtil;
import org.liaoinstan.springlayout.SpringLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016/9/13 13:57
 * 简述：
 */
public class FloatLayoutActivity extends SimpleActivity {
    private FloatIndicatorLayout floatTitleLayout;
    private SpringLayout springLayout;
    /**
     * ViewPager 的 子控件，ListView 或者 ScrollView 滑动到了
     */
    protected boolean isChildOnTop;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_float_layout;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        setBackView(R.id.iv_back);
        ViewGroup bodyLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_float_layout_recycler_view, null);
        RecyclerView recyclerView = (RecyclerView) bodyLayout.findViewById(R.id.rv);
        recyclerView.addOnScrollListener(new MyOnScrollListener());
        BeforeGameScoreAdapter adapter = new BeforeGameScoreAdapter(activity);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        adapter.addItem(list);
        recyclerView.setAdapter(adapter);
        springLayout = (SpringLayout) bodyLayout.findViewById(R.id.sl);
        springLayout.setOnLoadMoreListener(new MyOnLoadMoreListener());
        floatTitleLayout = (FloatIndicatorLayout) findViewById(R.id.ftl);
        floatTitleLayout.setIndicatorMarginTop(48);
        View indicatorLayout = LayoutInflater.from(context).inflate(R.layout.layout_float_layout_indicator, null);
        floatTitleLayout.setFloatIndicatorLayout(R.layout.layout_top, indicatorLayout, R.layout.layout_float_layout_indicator, bodyLayout);
        floatTitleLayout.setOnFloatScrollListener(new MyOnFloatTitleScrollListener());
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
            isChildOnTop = recyclerView.computeVerticalScrollOffset() == 0;
            floatTitleLayout.setIsChildOnTop(isChildOnTop);
        }
    }

    private final class MyOnFloatTitleScrollListener implements FloatIndicatorLayout.OnFloatScrollListener {
        @Override
        public void onFloatTitleScroll(int maxDistance, int currDistance, float progress) {
            LogUtil.e("maxDistance = " + maxDistance + " currDistance = " + " progress = " + progress);
        }
    }
}
