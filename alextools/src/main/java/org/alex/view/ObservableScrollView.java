package org.alex.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 作者：Alex
 * 时间：2016/9/13 11:01
 * 简述：
 */
public class ObservableScrollView extends ScrollView {
    private OnScrollListener onScrollListener = null;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollListener != null) {
            onScrollListener.onScroll(this, x, y, oldx, oldy);
        }
    }

    public interface OnScrollListener {
        void onScroll(ScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
