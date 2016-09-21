package com.alex.mvpapp.ui.zhihu;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.SimpleActivity;
import com.alex.mvpapp.config.Util;

import org.alex.adapter.TitleFragmentPagerAdapter;
import org.alex.callback.OnFragmentSelectedListener;
import org.alex.callback.SimpleOnPageChangeListener;
import org.alex.util.FontUtil;
import org.alex.util.ObjUtil;
import org.astuetz.pagerslidingtabstrip.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class ZhiHuActivity extends SimpleActivity {
    protected ArrayList<OnFragmentSelectedListener> listOnFragmentSelectedListener;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_zhihu;
    }

    /**
     * 获取启动者通过Intent传递过来的 数据
     *
     * @param map
     */
    @Override
    public void onGetIntentData(Intent intent, Map map) {
        super.onGetIntentData(intent, map);
        Util.printMap(map);
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        listOnFragmentSelectedListener = new ArrayList<>();
        setText(R.id.tv_title, "知乎日报");
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.psts);
        pagerSlidingTabStrip.setTypeface(FontUtil.typeface, Typeface.BOLD);
        ViewPager viewPager = findView(R.id.vp);
        List<Fragment> list = new ArrayList<Fragment>();
        List<String> listTitle = new ArrayList<String>();
        String strAry[] = new String[]{"头条", "互联网安全", "体育日报", "还有谁在无聊"};
        for (int i = 0; i < strAry.length; i++) {
            ZhiHuFragment zhiHuFragment = ZhiHuFragment.getInstance(i);
            list.add(zhiHuFragment);
            listOnFragmentSelectedListener.add(zhiHuFragment.new MyOnFragmentSelectedListener());
            listTitle.add(strAry[i]);
        }
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), list, listTitle);
        viewPager.setAdapter(adapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setOnPageChangeListener(new ViewPagerOnPageChangeListener());
    }

    private final class ViewPagerOnPageChangeListener extends SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            indexSelected = position;
            onHandOnFragmentSelectedListener();
        }
    }

    protected void onHandOnFragmentSelectedListener() {
        if (indexSelected < 0) {
            indexSelected = 0;
            return;
        }
        for (int i = 0; (listOnFragmentSelectedListener != null) && i < listOnFragmentSelectedListener.size(); i++) {
            ((OnFragmentSelectedListener) listOnFragmentSelectedListener.get(i)).onFragmentSelected(indexSelected, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyOnFragmentSelectedListener();
    }

    /**
     * 销毁  OnFragmentSelectedListener 监听器
     */
    protected void onDestroyOnFragmentSelectedListener() {
        if (ObjUtil.isNotEmpty(listOnFragmentSelectedListener)) {
            listOnFragmentSelectedListener.clear();
        }
        listOnFragmentSelectedListener = null;
    }

}
