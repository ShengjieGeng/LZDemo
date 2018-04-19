package com.melon.szlz.lzdemo.utils;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Toast;

public class ClickUtils {

    int interval = 500;
    private long[] mHits;
    private Context context;
    public ClickUtils() {
        // 数组长度表示要点击的次数,默认3
        this(3);
    }

    public ClickUtils(int count) {
        mHits = new long[count];
    }
    public void click(CustomClickListener listener) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();// 开机后开始计算的时间
        if (mHits[0] >= (SystemClock.uptimeMillis() - interval)) {
            listener.onClick();
        }
    }
    public interface CustomClickListener{
        void onClick();
    }
}
