package org.alex.callback.refreshloadmore;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:04
 * 简述：
 */

public interface OnLoadMoreListener extends OnRefreshListener {
    /**
     * 上拉加载，回调接口
     */
    void onLoadMore();
}

