package org.alex.swipebacklayout;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public abstract class OnSwipeBackListener {
    /**
     * 滑动 进度 的 监听 [0, 100]
     */
    public void onSwipeProgress(int progress) {
    }

    /**
     * 滑动结束 的监听
     */
    public abstract void onFinish();
}
