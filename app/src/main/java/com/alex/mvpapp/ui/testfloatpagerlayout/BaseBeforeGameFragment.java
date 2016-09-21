package com.alex.mvpapp.ui.testfloatpagerlayout;

import android.content.Context;

import com.alex.mvpapp.base.BaseFragment;

import org.alex.callback.OnFragmentSelectedListener;
import org.alex.mvp.CancelablePresenter;

/**
 * 作者：Alex
 * 时间：2016/9/13 11:15
 * 简述：
 */
public abstract class BaseBeforeGameFragment<P extends CancelablePresenter> extends BaseFragment<P> {
    protected FloatPagerLayoutActivity floatPagerLayoutActivity;
    /**
     * ViewPager 的 子控件，ListView 或者 ScrollView 滑动到了
     */
    protected boolean isChildOnTop;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        floatPagerLayoutActivity = (FloatPagerLayoutActivity) getActivity();
    }

    public final class MyOnFragmentSelectedListener implements OnFragmentSelectedListener {
        /**
         * 被选中的 Fragment的下标
         *
         * @param indexSelected
         */
        @Override
        public void onFragmentSelected(int indexSelected, Object extra) {
            if (index != indexSelected) {
                return;
            }
            if ((index == indexSelected) && (floatPagerLayoutActivity != null) && (floatPagerLayoutActivity.getFloatTitleLayout() != null)) {
                floatPagerLayoutActivity.getFloatTitleLayout().setIsChildOnTop(isChildOnTop);
            }
        }
    }
}
