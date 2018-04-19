package com.melon.szlz.lzdemo.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

@SuppressLint("SimpleDateFormat")
public class SimpleDateUtil {
	public static String format(String date) {
		long n = Long.parseLong(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
		String time = format.format(n);
		return time;
	}

	public static String formatTime(String date) {
		long n = Long.parseLong(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		String time = format.format(n);
		time = time.substring(time.lastIndexOf("/") + 1, time.lastIndexOf(":"));
		return time;
	}

	public static int formatLong(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		int TIM = Integer.parseInt(time.substring(0, 8));
		return TIM;
	}

	public static String formatTime(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		return time;
	}

	/**
	 * 接受消息解析消息时间值
	 * 
	 * @param date
	 * @return
	 */
	public static long formatBig(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		long time = Long.parseLong(format.format(date));
		return time;
	}

	public static String formatLongCurrent(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mm = format.format(date);
		String time = mm.substring(0, 10);
		return time;
	}

	public static String formatMessage(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		String time = format.format(date);
		return time;
	}

}
