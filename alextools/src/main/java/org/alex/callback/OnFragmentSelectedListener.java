package org.alex.callback;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:06
 * 简述：
 */

@SuppressWarnings("all")
public interface OnFragmentSelectedListener {
    /**
     * 被选中的 Fragment的下标
     */
    void onFragmentSelected(int indexSelected, Object extra);
}

