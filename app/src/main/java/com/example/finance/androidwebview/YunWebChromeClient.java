package com.example.finance.androidwebview;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/10/16.
 */

public class YunWebChromeClient extends WebChromeClient {

    private JsCallback  mJsCallback;
    private ProgressBar mProgressBar;


    public YunWebChromeClient(ProgressBar progressBar, JsCallback jsCallback) {
        this.mJsCallback = jsCallback;
        this.mProgressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Log.d("JS", " progress:" + newProgress);
        if (newProgress < 100) {
            if (View.VISIBLE != mProgressBar.getVisibility()) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            mProgressBar.setProgress(newProgress);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

        super.onProgressChanged(view, newProgress);
    }

    /**
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result    返回数据到web端
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

        //格式："js://android?arg1=111&arg2=222"
        Uri uri = Uri.parse(message);
        if (uri.getScheme().equals("js") && uri.getAuthority().equals("android")) {
            Log.d("JS", "uri:" + uri.toString() + "  url:" + url + " defaultValue:" + defaultValue + " result:" + result.toString());

            String func = uri.getQueryParameter("func");
            if (!TextUtils.isEmpty(func)) {
                String value = mJsCallback.onFuncationFinished(func, uri);
                if (!TextUtils.isEmpty(value)) {
                    result.confirm(value);
                }
            }

            return true;
        }

        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}
