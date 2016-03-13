package com.example.slf.android_task;

/**
 * Created by Huhu on 8/21/15.
 * 防止按钮被点击两次的工具类
 */
public class DoubleClickJuage {

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 5000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}