package com.alex.mvpapp.ui.testfloatpagerlayout;

import android.content.Context;
import android.view.View;

import com.alex.mvpapp.R;

import org.alex.adapter.SingleRecyclerAdapter;
import org.alex.adapter.core.RecyclerViewHolder;

/**
 * 作者：Alex
 * 时间：2016/9/13 13:17
 * 简述：
 * 宿主：
 * --------{@link BeforeGameScoreFragment}
 */
public class BeforeGameScoreAdapter extends SingleRecyclerAdapter<String> {
    public BeforeGameScoreAdapter(Context context) {
        super(context, R.layout.item_fragment_game_before_score);
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     * holder.setText(R.id.tv_right, bean.money);
     * holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     *
     * @param holder
     * @param position
     */
    @Override
    public void onConvert(RecyclerViewHolder holder, int position) {

    }

    /**
     * @param v        被点击 的 按钮
     * @param position 被点击的 position
     */
    @Override
    public void onPositionClick(View v, int position) {

    }
}
