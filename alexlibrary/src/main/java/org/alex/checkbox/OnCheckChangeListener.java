package org.alex.checkbox;

/**
 * 作者：Alex
 * 时间：2016年09月08日    23:33
 * 简述：
 */

public interface OnCheckChangeListener {

    /**
     * @param isChecked 被选中
     */
    void OnCheckChange(boolean isChecked, CheckBox checkBox);
}
