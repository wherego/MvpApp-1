package org.alex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.alex.util.ObjUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:48
 * 简述：
 */

@SuppressWarnings("all")
public class TitleFragmentPagerAdapter extends FragmentPagerAdapter {
    protected List<Fragment> list;
    protected List<String> listTitle;

    public TitleFragmentPagerAdapter(FragmentManager fm) {
        this(fm, null, null);
    }

    public TitleFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        this(fm, list, null);
    }

    public TitleFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> listTitle) {
        super(fm);
        if (ObjUtil.isEmpty(list)) {
            list = new ArrayList<>();
        }
        if (ObjUtil.isEmpty(listTitle)) {
            listTitle = new ArrayList<>();
        }
        this.list = list;
        this.listTitle = listTitle;
        notifyDataSetChanged();
    }

    public void addTitle(List<String> listTitle) {
        if (listTitle == null || listTitle.size() <= 0 || this.listTitle == null) {
            return;
        }
        this.listTitle.addAll(listTitle);
        notifyDataSetChanged();
    }

    public void addTitle(String... title) {
        List<String> listTitle = new ArrayList<>();
        for (int i = 0; (title != null) && (i < title.length); i++) {
            listTitle.add(title[i]);
        }
        addTitle(listTitle);
    }

    public void addItem(Fragment fragment) {
        if (fragment == null || list == null) {
            return;
        }
        list.add(fragment);
        notifyDataSetChanged();
    }

    public void addItem(Fragment... fragment) {
        List<Fragment> listFragment = new ArrayList<>();
        for (int i = 0; (fragment != null) && (i < fragment.length); i++) {
            listFragment.add(fragment[i]);
        }
        addItem(listFragment);
    }

    public void addItem(List<Fragment> list) {
        if (list == null || list.size() <= 0 || this.list == null) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ((list == null) ? null : list.get(position));
    }

    @Override
    public int getCount() {
        return ((list == null) ? 0 : list.size());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (listTitle == null) ? "" : listTitle.get(position);
    }
}

