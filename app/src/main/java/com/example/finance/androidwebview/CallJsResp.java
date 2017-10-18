package com.example.finance.androidwebview;

/**
 * Description:
 * Creator: Yanghj
 * Email: yanghj11@163.com
 * Date: 2017/10/18
 */

public class CallJsResp extends BaseResponse {
    private int age;
    private String occupation;

    public int getAge() {
        return age;
    }

    public String getOccupation() {
        return occupation;
    }

    @Override
    public String toString() {
        return "CallJsResp{" +
                "age=" + age +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}
