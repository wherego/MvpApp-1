package com.alex.mvpapp.base;

import org.alex.mvp.CancelablePresenter;

/**
 * 作者：Alex
 * 时间：2016/9/14 09:24
 * 简述：
 */
public abstract class SimpleTabActivity extends BaseTabActivity<CancelablePresenter> {

    /**
     * 创建 Presenter
     */
    @Override
    protected CancelablePresenter createPresenter() {
        return new CancelablePresenter(this);
    }

}
