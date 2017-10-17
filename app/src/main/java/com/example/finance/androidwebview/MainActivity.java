package com.example.finance.androidwebview;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements ValueCallback<String>,JsCallback {

    private WebView mWebView;
    private String  mRemoteUrl = "";
    private ProgressBar mProgressBar;
    private YunWebViewClient  mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRemoteUrl = /*"http://wap.baidu.com"; //*/"file:///android_asset/android_js.html";
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        initWebView();

    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(false);
        }

        webSettings.setAllowFileAccess(false);

        // 禁止 file 协议加载 JavaScript
//        if (mRemoteUrl.startsWith("file://")) {
//            webSettings.setJavaScriptEnabled(false);
//        } else {
            webSettings.setJavaScriptEnabled(true);
//        }

        mWebView.setWebChromeClient(new YunWebChromeClient(mProgressBar, this));
        mWebViewClient = new YunWebViewClient(mWebView, this);
        mWebViewClient.setInitCall("callJS1");
        mWebView.setWebViewClient(mWebViewClient);

        mWebView.loadUrl(mRemoteUrl);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                mWebViewClient.makeCallJs("callJS1");

                break;
            case R.id.button2:
                mWebViewClient.makeCallJs("callJS", 3, 5);
                break;
            case R.id.back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    this.finish();
                }
                break;
            case R.id.forward:
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
                break;
        }

    }

    /**
     * 本地调用h5后，h5返回的js处理值。
     * @param value
     */
    @Override
    public void onReceiveValue(String value) {
        Log.d("JS", "js return value:" + value);
    }

    /**
     *  js调用本地方法
     * @param function  方法名
     * @param uri   通过uri.getQueryParameter()来获得key所对应的value
     * @return  以String格式返回h5页面需要的数据
     */
    @Override
    public String onFuncationFinished(String function, Uri uri) {
        switch (function) {
            case "getLocalInfo":
                return getLocalInfo(uri.getQueryParameter("arg1"), uri.getQueryParameter("arg2"));
        }

        return null;
    }

    /***************************************************************
     *  以下部分为js调用本地的实际方法
     */

    private String getLocalInfo(String value1, String value2) {
        Log.d("JS", "js return value:" + value1 + "  value2:" + value2);
        return "lasdjfalsdf";
    }
}
