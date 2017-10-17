package com.example.finance.androidwebview;

import android.net.Uri;

/**
 * Created by Administrator on 2017/10/16.
 */

public interface JsCallback {
    String onFuncationFinished(String function, Uri value);
}
