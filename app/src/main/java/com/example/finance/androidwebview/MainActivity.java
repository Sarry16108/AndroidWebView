package com.example.finance.androidwebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("remoteUrl", /*"http://wap.baidu.com");//*/"file:///android_asset/android_js.html");
                startActivity(intent);
                break;

        }

    }

}
