package com.melon.szlz.lzdemo;

import android.app.Application;
import com.melon.szlz.lzdemo.beans.Book;
import com.melon.szlz.lzdemo.utils.Contacts;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 日期：2018/4/9
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public class MyApplication extends Application {
    public static MyApplication instance;
    private Book book;
    private boolean appForeground;

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            refWatcher = LeakCanary.install(this);
        }
    }
    private String currentScene = Contacts.SCENE_NORMAL;
    public void setSecne(String secne) {
        currentScene = secne;
    }

    public String getScene() {
        return currentScene;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public boolean isAppForeground() {
        return appForeground;
    }
}
