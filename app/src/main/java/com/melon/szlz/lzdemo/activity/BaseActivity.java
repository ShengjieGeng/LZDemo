package com.melon.szlz.lzdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.Toast;

import com.melon.szlz.lzdemo.MyApplication;
import com.melon.szlz.lzdemo.utils.PlayUtils;

import java.util.Timer;

/**
 * 日期：2018/4/11
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "LLLL";
    public MyApplication myApplication;
    private Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        myApplication = (MyApplication) getApplication();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract int getContentView();
    public abstract  void init();

    public void printLog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
                } else {
                    toast.setText(msg);
                }
                toast.show();
            }
        });
    }
}
