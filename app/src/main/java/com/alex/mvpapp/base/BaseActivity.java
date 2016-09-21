package com.alex.mvpapp.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alex.mvpapp.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.alex.annotation.Status;
import org.alex.helper.ViewHelper;
import org.alex.model.ParcelableMap;
import org.alex.model.StatusLayoutModel;
import org.alex.mvp.BaseHttpContract;
import org.alex.mvp.CancelablePresenter;
import org.alex.swipebacklayout.SwipeBackLayout;
import org.alex.util.FontUtil;
import org.alex.util.KeyPadUtil;
import org.alex.util.LogUtil;
import org.alex.util.ObjUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public abstract class BaseActivity<P extends CancelablePresenter> extends AppCompatActivity implements BaseHttpContract.View, View.OnClickListener {

    /**
     * 不可以滑动返回
     */
    protected boolean cannotSwipeBack;
    protected Context context;
    protected Activity activity;
    /**
     * Pre 的基类
     */
    protected P presenter;
    private ViewHelper viewHelper;
    private SystemBarTintManager tintManager;
    private Subscription subscription;
    private CompositeSubscription compositeSubscription;
    /**
     * 上次点击的时间
     */
    private static long lastClickTime;
    protected SwipeBackLayout swipeBackLayout;
    /**
     * 当前被展示的fragment 的下标
     */
    protected int indexSelected;

    /**
     * 返回按钮
     */
    private List<Integer> backViewIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indexSelected = -1;
        swipeBackLayout = new SwipeBackLayout(this);
        context = this;
        activity = this;
        presenter = createPresenter();
        viewHelper = new ViewHelper(this);
        backViewIdList = new ArrayList<>();
        if ((Status.NO_RES_ID != getLayoutResId()) && (0 != getLayoutResId())) {
            setContentView(getLayoutResId());
        }
        initLoadingDialog();
        getStatusLayoutModel();
        getBodyViewId();
        viewHelper.initMultiModeBodyLayout(this, getBodyViewId());
        initStatusBar();
        /*获取上个页面 传递的数据*/
        Map<String, Object> intentMap = getStringObjectMap();
        onGetIntentData(getIntent(), intentMap);
        onCreateData();
        FontUtil.setFontTypeface(findViewById(android.R.id.content));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!cannotSwipeBack) {
            swipeBackLayout.attachActivity(this);
        }
    }

    /**
     * 创建 Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取上个页面 传递的数据
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Map<String, Object> intentMap = getStringObjectMap();
        onGetIntentData(intent, intentMap);
    }

    @NonNull
    private Map<String, Object> getStringObjectMap() {
        Bundle intentBundle = getIntent().getParcelableExtra(ParcelableMap.extraBundle);
        List<String> bundleKeyList = getIntent().getStringArrayListExtra(ParcelableMap.bundleKey);
        Map<String, Object> intentMap = new HashMap<>();
        for (int i = 0; (bundleKeyList != null) && (i < bundleKeyList.size()); i++) {
            String key = bundleKeyList.get(i);
            Object value = intentBundle.get(key);
            intentMap.put(key, value);
        }
        return intentMap;
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public abstract int getLayoutResId();

    /**
     * 获取 主布局视图 的 id
     */
    @Override
    public int getBodyViewId() {
        return Status.NO_RES_ID;
    }

    /**
     * 获取启动者通过Intent传递过来的 数据
     */
    public void onGetIntentData(Intent intent, Map<String, Object> map) {

    }

    /**
     * 给控件添加点击事件
     *
     * @param ids
     */
    @Override
    public void setOnClickListener(@IdRes int... ids) {
        for (int i = 0; (ids != null) && (i < ids.length); i++) {
            View view = findViewById(ids[i]);
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    /**
     * 展示吐司
     */
    @Override
    public void toast(@NonNull String text) {
        if (viewHelper != null) {
            viewHelper.toast(context, text);
        }
    }

    @Override
    public void initLoadingDialog() {
        if (viewHelper != null) {
            viewHelper.initLoadingDialog();
        }
    }

    /**
     * 展示延时对话框
     */
    @Override
    public void showLoadingDialog() {
        if (viewHelper != null) {
            viewHelper.showLoadingDialog();
        }
    }

    /**
     * 隐藏延时对话框
     */
    @Override
    public void dismissLoadingDialog() {
        if (viewHelper != null) {
            viewHelper.dismissLoadingDialog();
        }
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        setBackView(R.id.iv_back);
    }

    /**
     * 设置 返回按钮
     */
    public void setBackView(int... id) {
        if (ObjUtil.isEmpty(id)) {
            return;
        }
        backViewIdList = (backViewIdList == null) ? new ArrayList<Integer>() : backViewIdList;
        for (int i = 0; i < id.length; i++) {
            backViewIdList.add(id[i]);
            View backView = findViewById(id[i]);
            if (backView != null) {
                backView.setOnClickListener(this);
            }
        }
    }

    /**
     * 设置 返回按钮
     */
    public void setBackView(@NonNull View... v) {
        if (ObjUtil.isEmpty(v)) {
            return;
        }
        for (int i = 0; i < v.length; i++) {
            setBackView(v[i].getId());
        }
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     * 不可以注释掉  super.onClick(v);
     */
    @Override
    public void onClick(View v) {
        if (isClickFrequently()) {
            return;
        }
        for (int i = 0; (backViewIdList != null) && (i < backViewIdList.size()); i++) {
            if (backViewIdList.get(i) == v.getId()) {
                finish();
                break;
            }
        }
    }

    /**
     * 判断点击过快 时间间隔 为 300毫秒
     */
    private boolean isClickFrequently() {
        long currClickTime = System.currentTimeMillis();
        if ((currClickTime - lastClickTime) < 300) {
            return true;
        }
        lastClickTime = currClickTime;
        return false;
    }

    /**
     * 页面跳转，无传值
     */
    public void startActivity(@NonNull Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 页面跳转，有传值
     */
    public <M extends ParcelableMap> void startActivity(@NonNull Class clazz, @NonNull M parcelableMap) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(ParcelableMap.extraBundle, parcelableMap.getBundle());
        intent.putStringArrayListExtra(ParcelableMap.bundleKey, parcelableMap.getKeyList());
        startActivity(intent);
    }

    @Override
    public StatusLayoutModel getStatusLayoutModel() {
        return StatusLayoutModel.defaultModel();
    }

    @Override
    public void showDefaultLayout() {
        viewHelper.showDefaultLayout();
    }

    @Override
    public void showLoadingLayout() {
        viewHelper.showLoadingLayout();
    }

    @Override
    public void showSuccessLayout() {
        viewHelper.showSuccessLayout();
    }

    @Override
    public void showEmptyLayout() {
        viewHelper.showEmptyLayout();
    }

    @Override
    public void setFailMessage(String message) {
        viewHelper.showFailLayout();
        viewHelper.setFailMessage(message);
    }

    /**
     * 给文本控件设置文本
     */
    @Override
    public void setText(View view, String text) {
        viewHelper.setText(view, text);
    }

    /**
     * 给文本控件设置文本
     */
    @Override
    public void setText(@IdRes int id, String text) {
        setText(findView(id), text);
    }

    /**
     * 将EditText的光标移至最后
     *
     * @param view
     */
    @Override
    public void setSelection(@NonNull View view) {
        if (viewHelper == null) {
            LogUtil.e("viewHelper 为空");
            return;
        }
        viewHelper.setSelection(view);
    }

    @Override
    public void showFailLayout() {
        if (viewHelper == null) {
            LogUtil.e("viewHelper 为空");
            return;
        }
        viewHelper.showFailLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (viewHelper != null) {
            viewHelper.cancelToast();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        KeyPadUtil.instance().closeKeyPad(this);

    }

    /**
     * 扩展的 findView
     */
    public <T extends View> T findView(@IdRes int id) {
        View view = findViewById(id);
        if (view == null) {
            return null;
        }
        return (T) view;
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2Px(@FloatRange(from = 0) float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    /**
     * 多状态布局的 点击事件
     */
    @Override
    public void onStatusLayoutClick(@Status int status) {

    }

    /**
     * 下拉刷新 或 加载 完成
     */
    @Override
    public void onRefreshFinish() {

    }

    /**
     * 得到上下文对象
     */
    @NonNull
    @Override
    public Context getViewContext() {
        return this;
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void initStatusBar() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null) {
            parentView.setFitsSystemWindows(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.qg_title_color_status_bar);
        }
    }

    /**
     * 设置状态栏的颜色值
     *
     * @param colorId 颜色id
     */
    public void setStatusBarTintResource(int colorId) {
        if (tintManager != null) {
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return KeyPadUtil.instance().canHiddenKeyPad(BaseActivity.this);
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public final class ScrollOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return KeyPadUtil.instance().canHiddenKeyPad(BaseActivity.this);
            }
            return false;
        }

    }

    /**
     * 添加 Subscription
     */
    @Override
    public void addSubscription(Subscription subscription) {
        compositeSubscription = (compositeSubscription == null) ? new CompositeSubscription() : compositeSubscription;
        compositeSubscription.add(subscription);
    }

    /**
     * 销毁一切资源，防止内存泄漏问题
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewHelper != null) {
            viewHelper.detachFromView();
            viewHelper = null;
        }
        tintManager = null;
        KeyPadUtil.instance().closeKeyPad(this);
        KeyPadUtil.detachView();
        onDetachFromView();
        if (presenter != null) {
            presenter.detachFromView();
        }
        presenter = null;
        activity = null;
        context = null;
    }

    /**
     * 解除订阅，防止内存泄漏
     */
    @Override
    public void onDetachFromView() {
        if (compositeSubscription != null) {
            /*防止 RxJava 出现内存泄漏问题*/
            compositeSubscription.clear();
            compositeSubscription = null;
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = null;
    }


    /*操作符 - 转换类**/
    public final class AddSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            addSubscription(subscriber);
            return subscriber;
        }
    }

    public final class ReplaceSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            subscription = subscriber;
            return subscriber;
        }
    }
}
