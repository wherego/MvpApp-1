package com.alex.mvpapp.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alex.mvpapp.R;
import com.alex.mvpapp.base.SimpleActivity;
import com.alex.mvpapp.config.AppCon;
import com.alex.mvpapp.config.Util;

import org.alex.view.ClickWebView;

import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class WebViewActivity extends SimpleActivity {
    private ClickWebView webView;
    private ProgressBar progressBar;
    private String url;
    private String title;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_webview;
    }

    /**
     * 获取启动者通过Intent传递过来的 数据
     *
     * @param map
     */
    @Override
    public void onGetIntentData(Intent intent, Map map) {
        super.onGetIntentData(intent, map);
        url = (String) map.get(AppCon.h5Url);
        title = (String) map.get(AppCon.h5Title);
        Util.printMap(map);
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        webView = findView(R.id.wv);
        progressBar = findView(R.id.pb);
        setText(R.id.tv_title, (title == null) ? "详情" : title);
        findViewById(R.id.iv_back).setOnClickListener(this);
        webView.applyNoCache();
        webView.loadUrl(url);
        /*背景透明*/
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        /*背景透明*/
        webView.setBackgroundColor(0);
        webView.setOnProgressChangedListener(new MyOnProgressChangedListener());
    }

    private final class MyOnProgressChangedListener implements ClickWebView.OnProgressChangedListener {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
            if (progress < 80) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.iv_back == v.getId()) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCookie(this);
    }
}
