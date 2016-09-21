package com.alex.mvpapp.ui.testfloatpagerlayout;

import android.widget.ScrollView;

import com.alex.mvpapp.R;

import org.alex.mvp.CancelablePresenter;
import org.alex.view.ClickWebView;
import org.alex.view.ObservableScrollView;

/**
 * 作者：Alex
 * 时间：2016/9/13 10:59
 * 简述：
 */
public class BeforeGameInfoStationFragment extends BaseBeforeGameFragment<CancelablePresenter> {
    private ClickWebView webView;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_before_info_station;
    }

    public static BeforeGameInfoStationFragment getInstance(int index) {
        BeforeGameInfoStationFragment fragment = new BeforeGameInfoStationFragment();
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
        webView = findView(R.id.wv);
        String url = "http://news.baidu.com/";
        webView.loadUrl(url);
        ObservableScrollView scrollView = findView(R.id.sv);
        scrollView.setOnScrollListener(new ObservableScrollListener());
    }

    private final class ObservableScrollListener implements ObservableScrollView.OnScrollListener {

        @Override
        public void onScroll(ScrollView scrollView, int x, int y, int oldx, int oldy) {
            isChildOnTop = (0 == scrollView.getScrollY());
            //KLog.e("scrollView.0 = "+(0 == scrollView.getScrollY()));
            if (floatPagerLayoutActivity.indexFragment == index) {
                floatPagerLayoutActivity.getFloatTitleLayout().setIsChildOnTop(isChildOnTop);
            }
        }
    }

}
