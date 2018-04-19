package com.melon.szlz.lzdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.melon.szlz.lzdemo.Launcher;

public class PackageChangedReceicer extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
            Intent intent2 = new Intent(context, Launcher.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }
        Log.d("llll", "onReceive: action"+action);
    }
}
