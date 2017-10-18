package com.example.finance.androidwebview;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Description:
 * Creator: Yanghj
 * Email: yanghj11@163.com
 * Date: 2017/10/17
 */

public class SecondActivity extends FragmentActivity  implements ValueCallback<String>,JsCallback {


    private WebView mWebView;
    private String  mRemoteUrl = "";
    private ProgressBar mProgressBar;
    private YunWebViewClient  mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mRemoteUrl = getIntent().getStringExtra("remoteUrl");
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
//        mWebViewClient.setInitCall(YunWebViewClient.JsCallJS1);
        mWebView.setWebViewClient(mWebViewClient);

        mWebView.loadUrl(mRemoteUrl);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                mWebViewClient.makeCallJs(YunWebViewClient.JsCallJS1);

                break;
            case R.id.button2:
                mWebViewClient.makeCallJs(YunWebViewClient.JsCallJS, 3, 5);
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
     * @param value   是Json字符串，里面包含func和其他的参数值
     */
    @Override
    public void onReceiveValue(String value) {
        Log.d("JS", "js return value:" + value);

        BaseResponse baseResponse = GsonUtils.castJsonObject(value, BaseResponse.class);
        switch (baseResponse.getFunc()) {
            case YunWebViewClient.JsCallJS1:
                Log.d("JS", GsonUtils.castJsonObject(value, CallJsResp.class).toString());
                break;
            case YunWebViewClient.JsCallJS:
                Log.d("JS", GsonUtils.castJsonObject(value, CallJsResp.class).toString());
                break;
        }
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
            case YunWebChromeClient.JavaLocalInfo:
                return getLocalInfo(uri.getQueryParameter("arg1"), uri.getQueryParameter("arg2"));
            case YunWebChromeClient.JavaStartActivity:
                toNewActivity();
                break;
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

    private void toNewActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("remoteUrl", "http://wap.baidu.com");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放缓存
        mWebView.clearCache(true);
    }
}
