package com.alex.mvpapp.ui;

import android.view.View;
import android.widget.TextView;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.SimpleActivity;
import com.alex.mvpapp.model.UserBean;
import com.alex.mvpapp.ui.douban.DouBanActivity;
import com.alex.mvpapp.ui.testdialog.DialogActivity;
import com.alex.mvpapp.ui.testfloatlayout.FloatLayoutActivity;
import com.alex.mvpapp.ui.testfloatpagerlayout.FloatPagerLayoutActivity;
import com.alex.mvpapp.ui.userman.LoginActivity;
import com.alex.mvpapp.ui.userman.UploadPhotoActivity;
import com.alex.mvpapp.ui.zhihu.ZhiHuActivity;

import org.alex.model.ParcelableMap;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class MainActivity extends SimpleActivity {
    private TextView tvContent;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        float dp2Px = dp2Px(-1);
        cannotSwipeBack = false;
        tvContent = findView(R.id.tv_content);
        setOnClickListener(R.id.tv_login, R.id.tv_float_layout, R.id.tv_add_img, R.id.tv_doubai, R.id.tv_zhihu, R.id.tv_dialog, R.id.tv_float_pager);
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        UserBean userBean = new UserBean();
        userBean.pwd = "123456";
        userBean.phone = "13146008029";
        ParcelableMap parcelableMap = new ParcelableMap().put("name", "张文亮").put("age", 24).put("isStu", true).put("userBean", userBean).put("userBean2", userBean);
        if (R.id.tv_login == v.getId()) {
            startActivity(LoginActivity.class);
        } else if (R.id.tv_add_img == v.getId()) {
            startActivity(UploadPhotoActivity.class, parcelableMap);
        } else if (R.id.tv_doubai == v.getId()) {
            startActivity(DouBanActivity.class, parcelableMap);
        } else if (R.id.tv_zhihu == v.getId()) {
            startActivity(ZhiHuActivity.class, parcelableMap);
        } else if (R.id.tv_dialog == v.getId()) {
            startActivity(DialogActivity.class);
        } else if (R.id.tv_float_pager == v.getId()) {
            startActivity(FloatPagerLayoutActivity.class);
        } else if (R.id.tv_float_layout == v.getId()) {
            startActivity(FloatLayoutActivity.class);
        }
    }

    @Override
    public void onBackPressed() {
        /*按返回键返回桌面，不结束Activity*/
        moveTaskToBack(true);
    }
}
