package com.melon.szlz.lzdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.melon.szlz.lzdemo.actions.BatteryAction;
import com.melon.szlz.lzdemo.activity.BaseActivity;
import com.melon.szlz.lzdemo.activity.ReadingActivity;
import com.melon.szlz.lzdemo.receivers.BatteryReceiver;
import com.melon.szlz.lzdemo.utils.Contacts;
import com.melon.szlz.lzdemo.utils.PlayUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static com.melon.szlz.lzdemo.utils.Contacts.baseAudioUri;

public class Launcher extends BaseActivity {
    private int remindTimes = 0;
    private Timer timer;
    private BatteryReceiver batteryReceiver;
    private TextView tvBattery;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }
    @Override
    public void init() {
        EventBus.getDefault().register(this);
        tvBattery = (TextView) findViewById(R.id.tv_battery);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataBattery(BatteryAction batteryAction) {
        tvBattery.setText("当前电量："+batteryAction.getLevel());
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                Log.d(TAG, "onReceive: 监听到网络广播");
                //得到网络连接管理器
                ConnectivityManager connectionManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                //通过管理器得到网络实例
                NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
                //判断是否连接
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        // 当前所连接的网络可用
                        Log.d(TAG, "onReceive: 网络已连接");
                        //判断是否第一次使用
                        SharedPreferences lzjy = getSharedPreferences("lzjy", MODE_PRIVATE);
                        boolean isFirstStart = lzjy.getBoolean("isFirstStart", true);
                        if (isFirstStart) {
                            //todo 第一次开机，执行操作
                            start();
                            Log.d(TAG, "onReceive: 第一次使用");
                            SharedPreferences.Editor edit = lzjy.edit();
                            edit.putBoolean("isFirstStart", false).commit();
                        } else {
                            Log.d(TAG, "onReceive: 正常使用");
                            playIntro();
                        }
                    } else {
                        Log.d(TAG, "onReceive: 请链接网络");
                    }
                    connectionManager = null;
                } else {
                    //todo 提示用户链接网络
                    Log.d(TAG, "onReceive: isConnected false");
                    Log.d(TAG, "onReceive: 请链接网络");
                }
            }
        }
    };
    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter1.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter1.addAction(Intent.ACTION_POWER_CONNECTED);
        batteryReceiver = new BatteryReceiver();
        registerReceiver(batteryReceiver, filter1);
    }

    private void start() {
        // 1，程序启动，播放第一段音频
        printLog("第一次见面");
        myApplication.setSecne(Contacts.SCENE_HELLO);
        PlayUtils.getInstance().play(baseAudioUri + File.separator + "403.wav", new PlayUtils.OnPlayListener() {
            @Override
            public void onFinish() {
                printLog("第一次见面音频播放完毕");
                playIntro();
            }
            @Override
            public void onError(String msg) {
            }
        });
    }

    private void playIntro() {
        printLog("播放功能介绍音频");
        myApplication.setSecne(Contacts.SCENE_INTORDUCE);
        PlayUtils.getInstance().play(baseAudioUri + File.separator + "404.wav", new PlayUtils.OnPlayListener() {
            @Override
            public void onFinish() {
                //3，当播放完毕功能介绍后启动一个timer，10s后无响应就再次提醒，有响应的话进入特定场景
                printLog("介绍完毕，启动timer（10s）");
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (remindTimes >= 1) {
                            //启动主题播放
                            printLog("主题音乐播放");
                            playThemeMusic();
                            remindTimes = 0;
                            cancel();
                        } else {//10s后无响应就再次提醒,播放完毕后再次启动timer
                            printLog("再次自我介绍");
                            playIntro();
                        }
                        remindTimes++;
                    }
                }, 10 * 1000);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    private void playThemeMusic() {
        myApplication.setSecne(Contacts.SCENE_MUSIC);
        PlayUtils.getInstance().play(baseAudioUri + File.separator + "511.wav", new PlayUtils.OnPlayListener() {
            @Override
            public void onFinish() {
                //主题音乐播放完毕
                Log.d(TAG, "onFinish: 主题音乐播放完毕");
                printLog("主题音乐播放完毕");
                playSleepRemind();
            }
            @Override
            public void onError(String msg) {

            }
        });
    }

    private void playSleepRemind() {
        printLog("开始播放独角戏");
        myApplication.setSecne(Contacts.SCENE_SLEEP_REMIND);
        PlayUtils.getInstance().play(baseAudioUri + File.separator + "406.wav", new PlayUtils.OnPlayListener() {
            @Override
            public void onFinish() {
                printLog("开始休眠");
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        printLog("待机一段时间后自动唤醒");
                        playComeBack("407.wav");
                        cancel();
                    }
                }, 20 * 1000);
            }

            @Override
            public void onError(String msg) {
            }
        });
    }
    private void playComeBack(String src) {
        myApplication.setSecne(Contacts.SCENE_COMEBACK);
        PlayUtils.getInstance().play(baseAudioUri + File.separator + src, new PlayUtils.OnPlayListener() {
            @Override
            public void onFinish() {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (remindTimes == 1) {
                            playComeBack("408.wav");
                            printLog("独白408");
                        } else if (remindTimes == 2) {
                            printLog("独白409");
                            playComeBack("409.wav");
                        } else if (remindTimes > 2) {
                            printLog("结束独白");
                            playIntro();
                            cancel();
                        }
                        remindTimes++;
                    }
                }, 5*1000);
            }
            @Override
            public void onError(String msg) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayUtils.getInstance().pause();
        stop();
        unregisterReceiver(myReceiver);
        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayUtils.getInstance().clear();
        EventBus.getDefault().unregister(this);
    }
    public void fun1(View view) {
        printLog("用户操作，接下来进入特定场景1");
        stop();
    }
    public void fun2(View view) {
        printLog("用户操作，接下来进入特定场景2");
        stop();
    }
    //读绘本
    public void fun3(View view) {
        stop();
        startActivity(new Intent(this, ReadingActivity.class));
    }

    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    //语音测评
    public void fun4(View view) {

    }

    public void fun5(View view) {

    }
}
