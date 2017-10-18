package com.example.finance.androidwebview;

import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Description:
 * Creator: Yanghj
 * Email: yanghj11@163.com
 * Date: 2017/10/16
 */

public class YunWebViewClient extends WebViewClient {

    private WebView mWebView;
    private ValueCallback<String>  mJsCallback = null;
    //初始调用h5的方法
    private String mFunction = null;

    /**
     *
     * @param webView
     * @param jsCallback    java调用h5后，返回数据回调方法
     */
    public YunWebViewClient(WebView webView, ValueCallback<String> jsCallback) {
        this.mWebView = webView;
        this.mJsCallback = jsCallback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            view.loadUrl(url);
            return true;
        }

        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        callJs(mFunction);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    /**
     *  h5页面加载后，初始调用的js方法
     * @param func
     * @param objects
     */
    public void setInitCall(String func, Object... objects) {
        mFunction = callJavaToJs(func, objects);
    }

    private void callJs(String callFunc) {
        if (TextUtils.isEmpty(callFunc)) {
            return;
        }

        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(callFunc, mJsCallback);
        } else {
            mWebView.loadUrl(callFunc);
        }
    }

    /**
     * 页面显示后，调用h5方法
     * @param func
     * @param objects
     */
    public void makeCallJs(String func, Object... objects) {
        String callFunc = callJavaToJs(func, objects);
        callJs(callFunc);
    }

    /**
     *  生成java调用h5页面方法所需要内容
     * @param func      所要调用的h5方法名
     * @param objects   h5方法所需要的参数
     * @return
     */
    public String callJavaToJs(String func, Object... objects) {
        if (TextUtils.isEmpty(func)) {
            return null;
        }

        StringBuilder builder = new StringBuilder("javascript:");
        builder.append(func);
        builder.append("(");

        for (Object obj : objects) {
            builder.append(obj);
            builder.append(",");
        }

        int len = builder.length();
        if (',' == builder.charAt(len - 1)) {
            builder.deleteCharAt(len - 1);
        }

        return builder.append(")").toString();
    }


    /****************************************************
     *   java调用js方法，协定的方法名
     */

    public static final String JsCallJS1 = "callJS1";
    public static final String JsCallJS  = "callJS";
}
