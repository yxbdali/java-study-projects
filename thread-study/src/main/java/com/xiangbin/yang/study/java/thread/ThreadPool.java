package com.xiangbin.yang.study.java.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * @author xiangbin.yang
 * @since 2017/11/13
 */
@Slf4j
public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        ExecutorService pool = Executors.newFixedThreadPool(4, new BasicThreadFactory.Builder().namingPattern("Thread_Pool-%d").build());
        for (int i = 0; i < 4; i++) {
            pool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("In sub thread");
                countDownLatch.countDown();
            });
        }
        pool.shutdown();
        countDownLatch.await();
        while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
            log.info("Wait for finished!");
        }
        log.info("Finished!");
    }
}
