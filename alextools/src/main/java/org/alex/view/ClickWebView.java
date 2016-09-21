package org.alex.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

/**
 * 作者：Alex
 * 时间：2016年09月03日    14:02
 * 简述：
 */

@SuppressWarnings("all")
@SuppressLint("SetJavaScriptEnabled")
public class ClickWebView extends WebView {
    /**
     * 可以长按   复制 粘贴
     */
    private boolean canLongClick;
    private OnProgressChangedListener onProgressChangedListener;

    public ClickWebView(Context context) {
        super(context);
        initView();
    }

    public ClickWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        canLongClick = false;
        /*屏蔽跳转到系统浏览器*/
        setWebViewClient(new MyWebViewClient());
        supportZoom();
        aboutImage();
        autoDisplayZoom();
        supportJavaScript();
    }

    /**
     * 关于加载图片
     */
    private void aboutImage() {
        WebSettings webSettings = getSettings();
        /*设置自动加载图片*/
        webSettings.setLoadsImagesAutomatically(true);
        //webSettings.setBlockNetworkImage(true);    //设置网页在加载的时候暂时不加载图片
    }

    /**
     * 支持 JS 和 访问本地文件
     */
    public void supportJavaScript() {
        if (isInEditMode()) {
            return;
        }
        WebSettings webSettings = getSettings();
        /*支持javascript*/
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /*可以访问文件*/
        webSettings.setAllowFileAccess(true);
        /*提高渲染的优先级*/
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    }

    /**
     * 自适应
     */
    private void autoDisplayZoom() {
        if (isInEditMode()) {
            return;
        }
        WebSettings webSettings = getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        /*缩放至屏幕的大小*/
        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        //Log.e("densityDpi", "densityDpi = " + density);
        if (density >= DisplayMetrics.DENSITY_HIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (density >= DisplayMetrics.DENSITY_MEDIUM) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (density >= DisplayMetrics.DENSITY_LOW) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
    }

    /**
     * 支持缩放功能
     */
    public void supportZoom() {
        if (isInEditMode()) {
            return;
        }
        WebSettings webSettings = getSettings();
        /*支持缩放功能  - 1*/
        webSettings.setSupportZoom(true);
        /*支持缩放功能  - 2*/
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
    }

    /**
     * 不使用缓存
     */
    public void applyNoCache() {
        WebSettings webSettings = getSettings();
        webSettings.setAppCacheEnabled(false);
        webSettings.setDatabaseEnabled(false);
        /*设置缓存模式*/
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /*设置缓存路径*/
        //webSettings.setAppCachePath("");
    }

    /**
     * 清理缓存
     */
    public void clearCookie(Context context) {
		/*Create a singleton CookieSyncManager within a context*/
		/*the singleton CookieManager instance*/
		/*forces sync manager to sync now*/
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.flush();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
        }

        getSettings().setJavaScriptEnabled(false);
        clearCache(true);
        clearHistory();
        clearFormData();
        context.deleteDatabase("webview.db");
        WebSettings webSettings = getSettings();

        String path = webSettings.getDatabasePath();
        File file = new File(path);
        deleteFile(file);
    }

    public void canLongClick(boolean canLongClick) {
        this.canLongClick = canLongClick;
    }

    /**
     * 去 左边页面
     */
    public boolean tryGoBack() {
        if (canGoBack()) {
            goBack();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 去 右边页面
     */
    private boolean tryGoForward() {
        if (canGoForward()) {
            goForward();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 禁止掉 WebView 的长按时间
     */
    @Override
    public boolean performLongClick() {
        if (!canLongClick) {
            return true;
        }
        return super.performLongClick();
    }

    /**
     * WebView 的 打开进度
     */
    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener;
        setWebChromeClient(new MyWebChromeClient());
    }

    /**
     * WebView 的 打开进度
     */
    public interface OnProgressChangedListener {
        /**
         * @param progress 当前 打开的进度
         */
        public void onProgressChanged(WebView view, int progress);
    }

    /**
     * WebView 的 打开进度
     */
    private final class MyWebChromeClient extends WebChromeClient {
        //获得网页的加载进度，显示在右上角的TextView控件中
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (onProgressChangedListener != null) {
                onProgressChangedListener.onProgressChanged(ClickWebView.this, newProgress);
            }
        }

        //获取Web页中的title用来设置自己界面中的title
        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
        //因此建议当触发onReceiveError时，不要使用获取到的title
        @Override
        public void onReceivedTitle(WebView view, String title) {
            //MainActivity.this.setTitle(title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }

        //处理alert弹出框，html 弹框的一种方式
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			/*这里  如果直接返回true ，会出现 js失效的问题*/
            return super.onJsAlert(view, url, message, result);
        }

        //处理confirm弹出框
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            //
            return true;
        }

        //处理prompt弹出框
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            //
            return true;
        }
    }

    /**
     * 屏蔽跳转到系统浏览器
     */
    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    private void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {

        }
    }

}
