package com.example.finance.androidwebview;

import android.net.Uri;

/**
 * Description:
 * Creator: Yanghj
 * Email: yanghj11@163.com
 * Date: 2017/10/16
 */

public interface JsCallback {
    String onFuncationFinished(String function, Uri value);
}
