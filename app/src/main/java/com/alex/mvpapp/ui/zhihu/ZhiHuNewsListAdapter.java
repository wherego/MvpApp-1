package com.alex.mvpapp.ui.zhihu;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.alex.adapter.SingleRecyclerAdapter;
import org.alex.adapter.core.RecyclerViewHolder;
import org.alex.model.ParcelableMap;
import com.alex.mvpapp.R;
import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.model.zhihu.NewsListBean;
import com.alex.mvpapp.ui.WebViewActivity;
import com.bumptech.glide.Glide;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class ZhiHuNewsListAdapter extends SingleRecyclerAdapter<NewsListBean.StoriesBean> {
    public ZhiHuNewsListAdapter(Context context) {
        super(context, R.layout.item_index_movie_list);
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     * holder.setText(R.id.tv_right, bean.money);
     * holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     */
    @Override
    public void onConvert(RecyclerViewHolder holder, int position) {
        NewsListBean.StoriesBean bean = list.get(position);
        String imgUrl = "https://www.baidu.com/";
        if ((bean.images != null) && (bean.images.size() >= 0) && (!TextUtils.isEmpty(bean.images.get(0)))) {
            imgUrl = bean.images.get(0);
        }
        Glide.with(context).load(imgUrl).placeholder(R.drawable.logo_empty).error(R.drawable.logo_empty).into((ImageView) holder.findView(R.id.iv_logo));
        holder.setText(R.id.tv, bean.title).setOnClickListener(position, R.id.layout_body);
    }

    @Override
    public void onPositionClick(View v, int position) {
        NewsListBean.StoriesBean bean = list.get(position);
        ParcelableMap map = new ParcelableMap();
        String title = "";
        String url = "";
        if ((bean != null)) {
            if (bean.title == null) {
                title = "标题";
            } else {
                title = bean.title;
            }
            if ((bean.images == null) || (bean.images.get(0) == null)) {
                url = "https://www.baidu.com/";
            } else {
                url = bean.images.get(0);
            }
        }
        map.put(AppCon.h5Title, title).put(AppCon.h5Url, url);
        startActivity(WebViewActivity.class, map);
    }
}
