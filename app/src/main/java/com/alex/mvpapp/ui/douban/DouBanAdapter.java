package com.alex.mvpapp.ui.douban;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.alex.adapter.SingleRecyclerAdapter;
import org.alex.adapter.core.RecyclerViewHolder;
import org.alex.model.ParcelableMap;
import com.alex.mvpapp.R;
import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.model.MovieListBean;
import com.alex.mvpapp.ui.WebViewActivity;
import com.bumptech.glide.Glide;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class DouBanAdapter extends SingleRecyclerAdapter<MovieListBean.SubjectsBean> {
    public DouBanAdapter(Context context) {
        super(context, R.layout.item_index_movie_list);
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     * holder.setText(R.id.tv_right, bean.money);
     * holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     */
    @Override
    public void onConvert(RecyclerViewHolder holder, int position) {
        MovieListBean.SubjectsBean bean = list.get(position);
        Glide.with(context).load(bean.images.large).placeholder(R.drawable.logo_empty).error(R.drawable.logo_empty).into((ImageView) holder.findView(R.id.iv_logo));
        holder.setText(R.id.tv, bean.title).setOnClickListener(position, R.id.layout_body);
    }

    @Override
    public void onPositionClick(View v, int position) {
        MovieListBean.SubjectsBean bean = list.get(position);
        ParcelableMap map = new ParcelableMap().put(AppCon.h5Url, bean.alt).put(AppCon.h5Title, bean.title);
        startActivity(WebViewActivity.class, map);
    }
}
