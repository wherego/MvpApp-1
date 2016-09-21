package com.alex.mvpapp.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.alex.mvp.CancelablePresenter;
import org.alex.util.ObjUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016/9/14 09:24
 * 简述：
 */
public abstract class BaseTabActivity<P extends CancelablePresenter> extends BaseActivity<P> {
    /***/
    private List<String> listFmName;

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        listFmName = (listFmName == null) ? new ArrayList<String>() : listFmName;
    }

    /**
     * @param fragment 添加进去的 fragment
     * @param position 需要默认展示的 下标
     */
    public void initFragment(int position, @IdRes int containerViewId, Fragment... fragment) {
        if (ObjUtil.isEmpty(fragment)) {
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragment.length; i++) {
            String simpleName = fragment[0].getClass().getSimpleName();
            listFmName.add(fragment[0].getClass().getSimpleName());
            if (position == i) {
                transaction.add(containerViewId, fragment[i], simpleName).show(fragment[i]);
            } else {
                transaction.add(containerViewId, fragment[i], simpleName).hide(fragment[i]);
            }
        }
        transaction.commit();
    }

    /**
     * 切换Fragment
     */
    public void switchFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String logMain = "切换 默认首页  manager = " + (manager == null) + " transaction = " + (transaction == null) + "\nlistFmName = " + listFmName + "\n";
        String logSub = "";
        for (int i = 0; i < listFmName.size(); i++) {
            if (i != position) {//隐藏的
                logSub += "\n隐藏 " + listFmName.get(i);
                transaction.hide(manager.findFragmentByTag(listFmName.get(i)));
            } else if (i == position) {//显示的
                logSub += "\n显示 " + listFmName.get(i);
                transaction.show(manager.findFragmentByTag(listFmName.get(position)));
            }
        }
        transaction.commit();
    }
}
