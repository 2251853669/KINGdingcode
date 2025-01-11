package com.kingdee.webapi.javasdk.src;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    GETSAL.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 40 * 60 * 1000); // 每40分钟执行一次

        // 添加定时任务，每三天清理一次存储的数据
        Timer cleanupTimer = new Timer();
        cleanupTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GETSAL.clearProcessedData();
            }
        }, 0, 3 * 24 * 60 * 60 * 1000); // 每三天执行一次
    }
}