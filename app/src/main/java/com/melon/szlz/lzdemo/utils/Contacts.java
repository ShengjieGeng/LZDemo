package com.melon.szlz.lzdemo.utils;

import android.os.Environment;
import android.support.annotation.IntDef;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * 日期：2018/4/9
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public class Contacts {
    public static final int a=0;
    public static final int s=0;
    public static final String baseAudioUri = Environment.getExternalStorageDirectory() + File.separator + "lzjy" + File.separator + "audios";
    public static final String SCENE_SLEEP_REMIND = "lzjy_scene_sleep_remind";
    public static final String SCENE_COMEBACK = "lzjy_scene_comeback";
    public static final String SCENE_NORMAL = "lzjy_scene_normal";
    public static final String SCENE_MUSIC = "lzjy_scene_music";
    public static final String SCENE_STORY = "lzjy_scene_story";
    public static final String SCENE_READING = "lzjy_scene_reading";
    public static final String SCENE_HELLO = "lzjy_scene_hello";
    public static final String SCENE_INTORDUCE = "lzjy_scene_introduce";
    public static final int ACTION_DOWN = 0x001;
    public static final int ACTION_MOVE = 0x002;
    public static final int ACTION_UP = 0x003;
}
