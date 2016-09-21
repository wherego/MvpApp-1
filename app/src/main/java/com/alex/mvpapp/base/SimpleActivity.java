package com.alex.mvpapp.base;

import org.alex.mvp.CancelablePresenter;

/**
 * 作者：Alex
 * 时间：2016年09月16日
 * 简述：
 */

public abstract class SimpleActivity extends BaseActivity<CancelablePresenter> {
    /**
     * 创建 Presenter
     */
    @Override
    protected CancelablePresenter createPresenter() {
        return new CancelablePresenter(this);
    }

}
