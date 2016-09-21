package org.alex.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.alex.adapter.callback.OnPositionClickListener;
import org.alex.adapter.core.IBaseAdapter;
import org.alex.adapter.core.RecyclerViewHolder;
import org.alex.model.ParcelableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年09月03日    09:54
 * 简述：
 */

@SuppressWarnings("all")
public abstract class MultipleRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements IBaseAdapter<T>, OnPositionClickListener {
    protected List<T> list;
    protected Context context;
    private Map<Integer, Integer> layoutResMap;

    public MultipleRecyclerAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<T>();
    }

    public MultipleRecyclerAdapter(Context context, @LayoutRes int... layoutRes) {
        this(context);
        layoutResMap = new HashMap<>();
        for (int i = 0; i < layoutRes.length; i++) {
            layoutResMap.put(i, layoutRes[i]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int layoutId = getLayoutRes(position);
        return getViewType(layoutId);
    }

    /**
     * 根据LayoutId获取ViewType
     */
    private int getViewType(int layoutId) {
        Iterator iter = layoutResMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Integer key = (Integer) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (val == layoutId) {
                return key;
            }
        }
        return 0;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new RecyclerViewHolder(this, inflater.inflate(layoutResMap.get(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        onConvert(holder, position);
    }

    /**
     * 根据 list.get(position) 的类型 获取 layoutRes
     */
    public abstract int getLayoutRes(int position);

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    /**
     * 获取数据集合
     */
    @Override
    public List getList() {
        return list;
    }

    /**
     * 在List最后一条进行追加
     *
     * @param bean
     */
    @Override
    public void addItem(T bean) {
        if ((list == null) || (bean == null)) {
            return;
        }
        list.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 在List最后一条进行追加
     *
     * @param bean
     * @param position
     */
    @Override
    public void addItem(T bean, int position) {
        if ((list == null) || (bean == null) || (list.size() <= position)) {
            return;
        }
        list.add(position, bean);
        notifyDataSetChanged();
    }

    /**
     * 在List最后一条进行追加
     *
     * @param bean
     */
    @Override
    public void addItem(T... bean) {
        if ((list == null) || (bean == null)) {
            return;
        }
        if (bean.length == 1) {
            list.add(bean[0]);
        } else {
            list.addAll(new ArrayList(Arrays.asList(bean)));
        }
        notifyDataSetChanged();
    }

    /**
     * 在List最后一条进行追加
     *
     * @param list
     */
    @Override
    public void addItem(List list) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 在List的position之后进行追加
     *
     * @param list
     * @param position
     */
    @Override
    public void addItem(List list, int position) {
        if ((this.list == null) || (list == null) || (list.size() <= position)) {
            return;
        }
        this.list.addAll(position, list);
        notifyDataSetChanged();
    }

    /**
     * 清空 并 刷新 list
     *
     * @param list
     */
    @Override
    public void refreshItem(List list) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清空 并 刷新 list
     *
     * @param bean
     */
    @Override
    public void refreshItem(T bean) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.clear();
        list.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 清空 并 刷新 list
     *
     * @param bean
     */
    @Override
    public void refreshItem(T... bean) {
        if ((list == null) || (bean == null)) {
            return;
        }
        this.list.clear();
        if (bean.length == 1) {
            list.add(bean[0]);
        } else {
            list.addAll(new ArrayList(Arrays.asList(bean)));
        }
        notifyDataSetChanged();
    }


    /**
     * 清空数据
     */
    @Override
    public void clearItem() {
        if (list == null) {
            return;
        }
        list.removeAll(list);
        notifyDataSetChanged();
    }

    /**
     * 移除一条数据
     *
     * @param bean
     */
    @Override
    public void removeItem(T bean) {
        if (list == null) {
            return;
        }
        list.remove(bean);
        notifyDataSetChanged();
    }

    /**
     * 移除多条数据
     *
     * @param list
     */
    @Override
    public void removeItem(List list) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.removeAll(list);
        notifyDataSetChanged();
    }

    /**
     * 移除一条数据
     *
     * @param position
     */
    @Override
    public void removeItem(int position) {
        if (list == null) {
            return;
        }
        list.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 更新一条数据
     *
     * @param bean
     * @param position
     */
    @Override
    public void updateItem(T bean, int position) {
        if ((list == null) || (list.size() <= position)) {
            return;
        }
        list.set(position, bean);
        notifyDataSetChanged();
    }

    /**
     * 页面跳转
     *
     * @param intent
     */
    @Override
    public void startActivity(@NonNull Intent intent) {
        context.startActivity(intent);
    }

    /**
     * 页面跳转
     */
    @Override
    public void startActivity(@NonNull Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * 页面跳转
     */
    @Override
    public <M extends ParcelableMap> void startActivity(@NonNull Class clazz, M parcelableMap) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(ParcelableMap.extraBundle, parcelableMap.getBundle());
        intent.putStringArrayListExtra(ParcelableMap.bundleKey, parcelableMap.getKeyList());
        context.startActivity(intent);
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     * holder.setText(R.id.tv_right, bean.money);
     * holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     */
    public abstract void onConvert(RecyclerViewHolder holder, int position);

}
