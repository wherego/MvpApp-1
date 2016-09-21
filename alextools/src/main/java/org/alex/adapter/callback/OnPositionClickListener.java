package org.alex.adapter.callback;

import android.view.View;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:51
 * 简述：
 */

@SuppressWarnings("all")
public interface OnPositionClickListener {
    /**
     * @param v     被点击 的 按钮
     * @param position 被点击的 position
     */
    void onPositionClick(View v, int position);
}

