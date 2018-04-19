package com.melon.szlz.lzdemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.melon.szlz.lzdemo.Launcher;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().endsWith(Intent.ACTION_BOOT_COMPLETED)) {
            Intent lz = new Intent(context, Launcher.class);
            lz.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lz);
        }
    }
}
