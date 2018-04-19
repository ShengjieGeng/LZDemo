package com.melon.szlz.lzdemo.activity;

import android.view.View;

import com.melon.szlz.lzdemo.R;
import com.melon.szlz.lzdemo.beans.Book;
import com.melon.szlz.lzdemo.utils.Contacts;
import com.melon.szlz.lzdemo.utils.FileUtil;
import com.melon.szlz.lzdemo.utils.PlayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.melon.szlz.lzdemo.utils.Contacts.baseAudioUri;

public class ReadingActivity extends BaseActivity {
    private Timer timer;
    @Override
    public int getContentView() {
        return R.layout.activity_reading;
    }

    @Override
    public void init() {
        myApplication.setSecne(Contacts.SCENE_READING);
        PlayUtils.getInstance().play(baseAudioUri + File.separator + "450.wav", new PlayUtils.OnPlayListener() {
            @Override
            public void onFinish() {
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //5s后没有看到书，提醒方书
                        printLog("5s后没有看到书，提醒方书");
                        PlayUtils.getInstance().play(baseAudioUri +File.separator+"451.wav");
                    }
                }, 5*1000);
            }
            @Override
            public void onError(String msg) {

            }
        });
    }
    /**
     * 用来监听第三方sdk发来的消息
     */
    public void receivedMessage(View view) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        printLog("收到书本信息，本地后台去加载这本书的神经网络");
        // 加载本地的数据
        FileUtil.getBookNerve("book1/nerve.json");
        printLog("提示用户翻页");
        PlayUtils.getInstance().play(baseAudioUri +File.separator+"453.wav");
        View pages = findViewById(R.id.pages);
        pages.setVisibility(View.VISIBLE);
    }
    public void page1(View view) {
        printLog("检测到是第一页");
        getPage(0);
    }
    public void page2(View view) {
        printLog("检测到是第二页");
        getPage(1);
    }
    public void page3(View view) {
        printLog("检测到是第三页");
        getPage(2);
    }
    @Override
    protected void onPause() {
        super.onPause();
        PlayUtils.getInstance().pause();
        stop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void getPage(int i) {
        Book book = myApplication.getBook();
        if (book == null) {
            printLog("书本不存在");
        } else {
            Book.PagesBean pagesBean = book.getPages().get(i);
            List<Book.PagesBean.ReadingsBean> readings = pagesBean.getReadings();
            List<String> reads = new ArrayList<>();
            for (Book.PagesBean.ReadingsBean readingsBean : readings) {
                reads.add(baseAudioUri +File.separator+readingsBean.getReading());
                printLog(readingsBean.getReading());
            }
            PlayUtils.getInstance().play( reads,0);
        }
    }

    public void finish(View view) {
        finish();
    }
}
