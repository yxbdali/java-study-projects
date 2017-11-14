package com.xiangbin.yang.study.java.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author xiangbin.yang
 * @since 2017/11/13
 */
public class TheadNum {
    private static final int tNum = 9;
    public static void main(String[] args) {
        ThreadGroup tGroup = new ThreadGroup("Thread group");
        for (int i = 0; i < tNum; i++) {
            int t = i;
            Thread thread = new Thread(tGroup, () -> {
                for (int j = 0; j < 10; j++) {

                    System.out.println(Thread.currentThread().getName() + ": " + t);
                }
                System.out.println("==========");
            }, String.format("Thread-%d", t));

            //thread.setDaemon(true);
            thread.start();
        }
    }
}
