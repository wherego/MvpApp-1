package com.alex.mvpapp.ui.testfloatpagerlayout;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.SimpleActivity;

import org.alex.adapter.TitleFragmentPagerAdapter;
import org.alex.callback.OnFragmentSelectedListener;
import org.alex.callback.SimpleOnPageChangeListener;
import org.alex.floatindicatorlayout.FloatIndicatorLayout;
import org.alex.util.LogUtil;
import org.hellojp.tabsindicator.TabsIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016/9/13 10:40
 * 简述：
 * 启动项：
 * ------------{@link BeforeGameDataFragment}
 * ------------{@link BeforeGameScoreFragment}
 * ------------{@link BeforeGameInfoStationFragment}
 */
public class FloatPagerLayoutActivity extends SimpleActivity {
    private View leftAnimLayout;
    private View rightAnimLayout;
    private RelativeLayout.LayoutParams leftAnimParams;
    private RelativeLayout.LayoutParams rightAnimParams;
    private int xMarginMin, xMarginMax, yMarginMin, yMarginMax;
    private float sizeMin, sizeMax;
    private List<Fragment> listFragment;
    private FloatIndicatorLayout floatTitleLayout;
    public static int indexFragment;
    protected ArrayList<OnFragmentSelectedListener> listOnFragmentSelectedListener;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_float_pager_layout;
    }

    public FloatIndicatorLayout getFloatTitleLayout() {
        return floatTitleLayout;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        initView();
    }

    private void initView() {
        listOnFragmentSelectedListener = new ArrayList<>();
        xMarginMin = (int) dp2Px(32);
        xMarginMax = (int) dp2Px(112);
        yMarginMin = (int) dp2Px(6);
        yMarginMax = (int) dp2Px(96);
        sizeMin = dp2Px(36);
        sizeMax = dp2Px(72);
        leftAnimLayout = findViewById(R.id.iv_anim_left);
        rightAnimLayout = findViewById(R.id.iv_anim_right);
        floatTitleLayout = (FloatIndicatorLayout) findViewById(R.id.ftl);
        floatTitleLayout.setIndicatorMarginTop(48);
        ViewGroup bodyLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_float_pager_body_view_pager, null);
        ViewPager viewPager = (ViewPager) bodyLayout.findViewById(R.id.vp);
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        listFragment = new ArrayList<>();
        BeforeGameDataFragment beforeGameDataFragment = BeforeGameDataFragment.getInstance(0);
        BeforeGameScoreFragment beforeGameScoreFragment = BeforeGameScoreFragment.getInstance(1);
        BeforeGameInfoStationFragment beforeGameInfoStationFragment = BeforeGameInfoStationFragment.getInstance(2);
        listFragment.add(beforeGameDataFragment);
        listFragment.add(beforeGameScoreFragment);
        listFragment.add(beforeGameInfoStationFragment);
        listOnFragmentSelectedListener.add(beforeGameDataFragment.new MyOnFragmentSelectedListener());
        listOnFragmentSelectedListener.add(beforeGameScoreFragment.new MyOnFragmentSelectedListener());
        listOnFragmentSelectedListener.add(beforeGameInfoStationFragment.new MyOnFragmentSelectedListener());
        adapter.addItem(listFragment);
        adapter.addTitle("赛前数据", "赛前情评分", "赛前情报站");

        View indicatorLayout = LayoutInflater.from(context).inflate(R.layout.layout_float_pager_indicator, null);
        TabsIndicator tabsIndicator = (TabsIndicator) indicatorLayout.findViewById(R.id.ti);
        tabsIndicator.setViewPager(0, viewPager);
        tabsIndicator.setAnimationWithTabChange(true);
        tabsIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
        floatTitleLayout.setFloatIndicatorLayout(R.layout.layout_top, indicatorLayout, R.layout.layout_float_pager_indicator, bodyLayout);
        floatTitleLayout.setOnFloatScrollListener(new MyOnFloatTitleScrollListener());
    }

    private final class MyOnFloatTitleScrollListener implements FloatIndicatorLayout.OnFloatScrollListener {
        @Override
        public void onFloatTitleScroll(int maxDistance, int currDistance, float progress) {

            int size = (int) (sizeMax - (sizeMax - sizeMin) * progress);
            leftAnimParams = (leftAnimParams == null) ? new RelativeLayout.LayoutParams(leftAnimLayout.getWidth(), leftAnimLayout.getHeight()) : leftAnimParams;
            rightAnimParams = (rightAnimParams == null) ? new RelativeLayout.LayoutParams(rightAnimLayout.getWidth(), rightAnimLayout.getHeight()) : rightAnimParams;
            leftAnimParams.leftMargin = (int) (xMarginMin + (xMarginMax - xMarginMin) * progress);
            leftAnimParams.topMargin = (int) (yMarginMax - (yMarginMax - yMarginMin) * progress);
            leftAnimParams.width = size;
            leftAnimParams.height = size;
            leftAnimLayout.setLayoutParams(leftAnimParams);
            rightAnimParams.rightMargin = (int) (xMarginMin + (xMarginMax - xMarginMin) * progress);
            rightAnimParams.topMargin = (int) (yMarginMax - (yMarginMax - yMarginMin) * progress);
            rightAnimParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightAnimParams.width = size;
            rightAnimParams.height = size;
            rightAnimLayout.setLayoutParams(rightAnimParams);
        }
    }

    private final class MyOnPageChangeListener extends SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            indexFragment = position;
            LogUtil.e("indexFragment = " + indexFragment);
        }

    }
}
