package com.melon.szlz.lzdemo.utils;

/**
 * 日期：2018/4/10
 * 作者：melon
 * 邮箱：960275325@qq.com
 * ----------------我是好人------------------
 */
public class ThreadPoolManager {
    private static class ThreadPoolHolder {
        private static PriorityExecutor executor = new PriorityExecutor(true);
    }
    public static PriorityExecutor getPriorityExecutor() {
        return ThreadPoolHolder.executor;
    }
}
