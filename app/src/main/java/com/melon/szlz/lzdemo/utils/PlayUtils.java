package com.melon.szlz.lzdemo.utils;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class PlayUtils {
    private static volatile PlayUtils instance;
    private MediaPlayer mediaPlayer;
    private static final String TAG = "llll";
    private int playIndex;
    private PlayUtils() {
    }
    public static PlayUtils getInstance() {
        PlayUtils temp = instance;
        if (temp == null) {
            synchronized (PlayUtils.class) {
                temp = instance;
                if (temp == null) {
                    temp = new PlayUtils();
                    instance = temp;
                }
            }
        }
        return temp;
    }
    public void play(String path) {
        play(path,1,null);
    }
    /**
     * 带有播放次数
     * @param path 路径
     * @param count 次数
     */
    private int mCount = 0;//记录播放次数
    public void play(final String path, final int count, final OnPlayListener listener) {
        play(path, new OnPlayListener() {
            @Override
            public void onFinish() {
                mCount++;
                if (mCount < count) {
                    play(path, count,listener);
                } else {
                    mCount = 0;
                    if (listener!=null)
                        listener.onFinish();
                }
            }
            @Override
            public void onError(String msg) {

            }
        });
    }

    public void play(String path, final OnPlayListener listener) {
        try {
            Log.d(TAG, "play: 获取的路径" + path);
            if (mediaPlayer == null) {
                Log.d(TAG, "play: mediaPlayer = null");
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    listener.onFinish();
                }

            });
        } catch (IOException e) {
            listener.onError("play: 文件不存在");
            e.printStackTrace();
        }
    }
    /**
     * 播放多个音频，有序播放
     * @param paths 音频地址数组
     * @param startIndex 播放的起始位置
     */
    public void play(final String[] paths, int startIndex) {
        playIndex = startIndex;
        play(paths[playIndex], new OnPlayListener() {
            @Override
            public void onFinish() {
                playIndex++;
                if (playIndex < paths.length) {
                    play(paths, playIndex);
                } else {
                    playIndex = 0;
                }
            }
            @Override
            public void onError(String msg) {
            }
        });
    }
    /**
     * 播放多个音频，有序播放
     * @param paths 音频地址list
     * @param startIndex 播放的起始位置
     */
    public void play(final List<String> paths, int startIndex) {
        playIndex = startIndex;
        play(paths.get(playIndex), new OnPlayListener() {
            @Override
            public void onFinish() {
                playIndex++;
                if (playIndex < paths.size()) {
                    play(paths, playIndex);
                } else {
                    playIndex = 0;
                }
            }
            @Override
            public void onError(String msg) {
            }
        });
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void clear() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public interface OnPlayListener {
        void onFinish();

        void onError(String msg);
    }
}
