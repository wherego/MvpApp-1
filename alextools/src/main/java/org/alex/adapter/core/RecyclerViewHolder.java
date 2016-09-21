package org.alex.adapter.core;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import org.alex.adapter.callback.OnPositionClickListener;
import org.alex.util.FontUtil;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:52
 * 简述：
 */

@SuppressWarnings("all")
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements IBaseViewHolder<RecyclerViewHolder> {
    private SparseArray<View> viewSparseArray;
    private SparseArray<PositionOnClickListener> onClickListenerSparseArray;
    private OnPositionClickListener onPositionClickListener;

    public RecyclerViewHolder(OnPositionClickListener onPositionClickListener, View itemView) {
        super(itemView);
        this.onPositionClickListener = onPositionClickListener;
        this.viewSparseArray = new SparseArray<>();
        this.onClickListenerSparseArray = new SparseArray<>();
        FontUtil.setFontTypeface(itemView);
    }

    /**
     * 通过id获取控件
     *
     * @param id
     * @return
     */
    @Override
    public <T extends View> T findView(int id) {
        View view = viewSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            viewSparseArray.put(id, view);
        }
        return (T) view;
    }


    /**
     * 找到 PositionOnClickListener
     */
    public <P extends PositionOnClickListener> P findClickListener(int id, int position) {
        PositionOnClickListener onClickListener = onClickListenerSparseArray.get(id);
        if (onClickListener == null) {
            onClickListener = new PositionOnClickListener(position);
            onClickListenerSparseArray.put(id, onClickListener);
        }
        onClickListener.position = position;
        return (P) onClickListener;
    }

    /**
     * 得到ConvertView
     */
    @Override
    public View getConvertView() {
        return itemView;
    }

    /**
     * 给TextView 设置文本
     */
    @Override
    public RecyclerViewHolder setText(@IdRes int id, String text) {
        TextView textView = findView(id);
        if ((textView != null) && (!TextUtils.isEmpty(text))) {
            textView.setText(text);
        }
        return this;
    }

    /**
     * 设置点击事件
     */
    @Override
    public RecyclerViewHolder setOnClickListener(int position, @IdRes int... id) {
        for (int i = 0; (id != null) && (i < id.length); i++) {
            View view = findView(id[i]);
            if (view != null) {
                view.setOnClickListener(findClickListener(id[i], position));
            }
        }
        return this;
    }

    /**
     * 设置点击事件
     */
    @Override
    public RecyclerViewHolder setOnClickListener(int position, @IdRes int id) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setOnClickListener(findClickListener(id, position));
        return this;
    }

    /**
     * 设置可见性
     */
    @Override
    public RecyclerViewHolder setVisibility(@IdRes int id, int visibility) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        if ((visibility == View.VISIBLE) || (visibility == View.GONE) || (visibility == View.INVISIBLE)) {
            view.setVisibility(visibility);
        }
        return this;
    }

    /**
     * 得到标签
     */
    @Override
    public <T> T getTag(@IdRes int id) {
        View view = findView(id);
        if (view == null) {
            return (T) "";
        }
        return (T) view.getTag();
    }

    /**
     * 得到标签
     */
    @Override
    public <T> T getTag(@IdRes int id, int key) {
        View view = findView(id);
        if (view == null) {
            return (T) "";
        }
        return (T) view.getTag(key);
    }

    /**
     * 设置标签
     */
    @Override
    public RecyclerViewHolder setTag(@IdRes int id, Object tag) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setTag(tag);
        return this;
    }

    /**
     * 设置标签
     */
    @Override
    public RecyclerViewHolder setTag(@IdRes int id, int key, Object tag) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setTag(key, tag);
        return this;
    }

    private final class PositionOnClickListener implements View.OnClickListener, OnPositionClickListener {
        public int position;

        public PositionOnClickListener(int position) {
            this.position = position;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            onPositionClick(v, position);
        }

        /**
         * @param v        被点击 的 按钮
         * @param position 被点击的 position
         */
        @Override
        public void onPositionClick(View v, int position) {
            onPositionClickListener.onPositionClick(v, position);
        }
    }
}

