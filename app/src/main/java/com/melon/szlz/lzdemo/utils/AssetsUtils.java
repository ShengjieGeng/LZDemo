package com.melon.szlz.lzdemo.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetsUtils {
    /**
     * 复制文件
     *
     * @param context 上下文对象
     * @param zipPath 源文件
     * @param targetPath 目标文件
     */
    public static void copy(Context context, String zipPath, String targetPath) {
        if (TextUtils.isEmpty(zipPath) || TextUtils.isEmpty(targetPath)) {
            return;
        }
        File dest = new File(targetPath);
        dest.getParentFile().mkdirs();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(context.getAssets().open(zipPath));
            out = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝assets文件下文件到指定路径
     *
     * @param context
     * @param assetDir  源文件/文件夹
     * @param targetDir  目标文件夹
     */
    public static void copyAssets(Context context, String assetDir, String targetDir) {
        if (TextUtils.isEmpty(assetDir) || TextUtils.isEmpty(targetDir)) {
            return;
        }
        String separator = File.separator;
        try {
            // 获取assets目录assetDir下一级所有文件以及文件夹
            String[] fileNames = context.getResources().getAssets().list(assetDir);
            // 如果是文件夹(目录),则继续递归遍历
            if (fileNames.length > 0) {
                File targetFile = new File(targetDir);
                if (!targetFile.exists() && !targetFile.mkdirs()) {
                    return;
                }
                for (String fileName : fileNames) {
                    copyAssets(context, assetDir + separator + fileName, targetDir + separator + fileName);
                }
            } else { // 文件,则执行拷贝
                copy(context, assetDir, targetDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
