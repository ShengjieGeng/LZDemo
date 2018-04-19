package com.melon.szlz.lzdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.melon.szlz.lzdemo.actions.BatteryAction;

import org.greenrobot.eventbus.EventBus;

import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

/**
 * 日期：2018/4/18
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            //你可以读到充电状态,如果在充电，可以读到是usb还是交流电
            // 是否在充电
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            // 怎么充
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            int powerLevel = intent.getIntExtra("level", 0);
            Log.d("llll", "onReceive: powerLevel = " + powerLevel);
            EventBus.getDefault().post(new BatteryAction(powerLevel));
        } else if (intent.getAction().equals(ACTION_POWER_CONNECTED)) {
            Log.d("llll", "Power Connected.");
        } else if (intent.getAction().equals(ACTION_POWER_DISCONNECTED)) {
           Log.d("llll", "Power DisConnected.");
        }
    }
}
