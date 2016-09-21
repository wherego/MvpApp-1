package org.alex.adapter.core;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.alex.adapter.callback.OnPositionClickListener;
import org.alex.util.FontUtil;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:50
 * 简述：
 */

@SuppressWarnings("all")
public class ListViewHolder implements IBaseViewHolder<ListViewHolder>{
    private SparseArray<View> viewSparseArray;
    private View convertView;
    private OnPositionClickListener onPositionClickListener;
    private SparseArray<PositionOnClickListener> onClickListenerSparseArray;

    private ListViewHolder(Context context, View itemView, ViewGroup parent) {
        this.convertView = itemView;
        this.onPositionClickListener = onPositionClickListener;
        this.viewSparseArray = new SparseArray<>();
        this.onClickListenerSparseArray = new SparseArray<>();
        this.convertView.setTag(this);
    }

    public static ListViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int LayoutRes, int position) {
        ListViewHolder holder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(LayoutRes, parent, false);
            holder = new ListViewHolder(context, itemView, parent);
            return holder;
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }
        FontUtil.setFontTypeface(holder.getConvertView());
        return holder;
    }
    private <P extends PositionOnClickListener> P findClickListener(int id, int position) {
        PositionOnClickListener onClickListener = onClickListenerSparseArray.get(id);
        if (onClickListener == null) {
            onClickListener = new PositionOnClickListener(position);
            onClickListenerSparseArray.put(id, onClickListener);
        }
        onClickListener.position = position;
        return (P) onClickListener;
    }
    /**
     * 通过id获取控件
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        View view = viewSparseArray.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            viewSparseArray.put(id, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 给TextView 设置文本
     */
    public ListViewHolder setText(int id, String text) {
        TextView textView = findView(id);
        if ((textView != null) && (!TextUtils.isEmpty(text))) {
            textView.setText(text);
        }
        return this;
    }

    public ListViewHolder setOnClickListener(int position, @IdRes int... id) {
        for (int i = 0; (id != null) && (i < id.length); i++) {
            View view = findView(id[i]);
            if (view != null) {
                view.setOnClickListener(findClickListener(id[i], position));
            }
        }
        return this;
    }

    public ListViewHolder setOnClickListener(int position, @IdRes int id) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setOnClickListener(findClickListener(id, position));
        return this;
    }

    public ListViewHolder setVisibility(int id, int visibility) {
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
     *
     * @param id
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
     *
     * @param id
     * @param key
     */
    @Override
    public <T> T getTag(@IdRes int id, int key) {
        View view = findView(id);
        if (view == null) {
            return (T) "";
        }
        return (T) view.getTag(key);
    }

    public ListViewHolder setTag(int id, Object tag) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setTag(tag);
        return this;
    }

    public ListViewHolder setTag(int id, int key, Object tag) {
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

