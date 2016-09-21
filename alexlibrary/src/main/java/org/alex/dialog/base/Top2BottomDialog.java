package org.alex.dialog.base;

import android.content.Context;

import org.alex.alexlibrary.R;
import org.alex.dialog.annotation.AnimType;

/**
 * 作者：Alex
 * 时间：2016年09月10日    17:33
 * 简述：
 */

public abstract class Top2BottomDialog<D extends Top2BottomDialog> extends SimpleDialog<D> {
    public Top2BottomDialog(Context context) {
        super(context, R.style.alex_dialog_base_light_style);

    }

    /**
     * 显示对话框，无动画
     */
    @Override
    public void show() {
        show(AnimType.TOP_2_BOTTOM);
    }

    /**
     * 显示对话框，强制转换对话框的动画类型
     *
     * @param animType
     */
    @Override
    public void show(@AnimType int animType) {
        super.show(animType);
    }
}
