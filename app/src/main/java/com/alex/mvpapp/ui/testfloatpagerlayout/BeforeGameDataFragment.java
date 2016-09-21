package com.alex.mvpapp.ui.testfloatpagerlayout;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alex.mvpapp.R;

import org.alex.helper.TextLineHelper;
import org.alex.mvp.CancelablePresenter;
import org.alex.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016/9/13 10:59
 * 简述：
 * 启动者：
 * ----------- {@link FloatPagerLayoutActivity}
 */
public class BeforeGameDataFragment extends BaseBeforeGameFragment<CancelablePresenter> {

    private List<TextLineHelper> listTextLineHelper;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_before_data;
    }

    public static BeforeGameDataFragment getInstance(int index) {
        BeforeGameDataFragment fragment = new BeforeGameDataFragment();
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
        ObservableScrollView scrollView = findView(R.id.sv);
        scrollView.setOnScrollListener(new ObservableScrollListener());
        setOnClickListener(R.id.tv_explore_1, R.id.tv_explore_2, R.id.tv_explore_3, R.id.tv_explore_4, R.id.tv_explore_5);
        initTextLineHelper(R.id.tv_content_1, R.id.tv_content_2, R.id.tv_content_3, R.id.tv_content_4, R.id.tv_content_5);
    }

    private void initTextLineHelper(@IdRes int... id) {
        listTextLineHelper = new ArrayList<>();
        MyOnLineFinishListener onLineFinishListener = new MyOnLineFinishListener();
        for (int i = 0; i < id.length; i++) {
            TextLineHelper textLineHelper = new TextLineHelper.Builder().textView((TextView) findView(id[i])).maxLines(10).minLines(2).min2MaxDuration(500).max2MinDuration(500).build();
            textLineHelper.onLineFinishListener(onLineFinishListener);
            listTextLineHelper.add(textLineHelper);
        }
    }

    private final class MyOnLineFinishListener implements TextLineHelper.OnLineFinishListener{

        /**
         * @param textView
         * @param isMin2Max 是 由最小行数 到 最大行数
         */
        @Override
        public void onLineFinish(TextView textView, boolean isMin2Max) {
            if(R.id.tv_content_1 == textView.getId()){
                ((TextView)findView(R.id.tv_explore_1)).setText(isMin2Max ? "收回" : "展开");
            }else if(R.id.tv_content_2 == textView.getId()){
                ((TextView)findView(R.id.tv_explore_2)).setText(isMin2Max ? "收回" : "展开");
            }else if(R.id.tv_content_3 == textView.getId()){
                ((TextView)findView(R.id.tv_explore_3)).setText(isMin2Max ? "收回" : "展开");
            }else if(R.id.tv_content_4 == textView.getId()){
                ((TextView)findView(R.id.tv_explore_4)).setText(isMin2Max ? "收回" : "展开");
            }else if(R.id.tv_content_5 == textView.getId()){
                ((TextView)findView(R.id.tv_explore_5)).setText(isMin2Max ? "收回" : "展开");
            }
        }
    }
    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_explore_1 == v.getId()) {
            listTextLineHelper.get(0).toggleTextLayout();
        } else if (R.id.tv_explore_2 == v.getId()) {
            listTextLineHelper.get(1).toggleTextLayout();
        } else if (R.id.tv_explore_3 == v.getId()) {
            listTextLineHelper.get(2).toggleTextLayout();
        } else if (R.id.tv_explore_4 == v.getId()) {
            listTextLineHelper.get(3).toggleTextLayout();
        } else if (R.id.tv_explore_5 == v.getId()) {
            listTextLineHelper.get(4).toggleTextLayout();
        }
    }

    private final class ObservableScrollListener implements ObservableScrollView.OnScrollListener {

        @Override
        public void onScroll(ScrollView scrollView, int x, int y, int oldx, int oldy) {
            isChildOnTop = (0 == scrollView.getScrollY());
            if (floatPagerLayoutActivity.indexFragment == index) {
                floatPagerLayoutActivity.getFloatTitleLayout().setIsChildOnTop(isChildOnTop);
            }
        }
    }


}
