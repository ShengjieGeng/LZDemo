package com.melon.szlz.lzdemo.utils;

import android.util.Log;
import java.io.DataOutputStream;

/**
 * 日期：2018/4/17
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public class ApkUtils {
    /**
     * 实现静默更新，卸载等cmd，需要获取系统root权限
     * <uses-permission android:name= "android.permission.RESTART_PACKAGES" />
     <uses-permission android:name="android.permission.INSTALL_PACKAGES" />
     * @param command
     * @return
     */
    public static boolean rootCommand(String command){
        Process process = null;
        DataOutputStream os = null;
        try{
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e){
            Log.d("llll", "ROOT REE" + e.getMessage());
            return false;
        } finally{
            try{
                if (os != null){
                    os.close();
                }
                process.destroy();
            } catch (Exception e){
            }
        }
        Log.d("llll", "Root SUC ");
        return true;
    }
}
