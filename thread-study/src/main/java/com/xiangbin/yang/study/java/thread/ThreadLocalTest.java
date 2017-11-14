package com.xiangbin.yang.study.java.thread;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;

/**
 * @author xiangbin.yang
 * @since 2017/11/13
 */
@Slf4j
public class ThreadLocalTest {
    public static void main(String[] args) {
        Data dat = new Data();

        Thread t1 = new Thread(() -> {
            dat.setNumber(2);
            dat.setNum(2);
            log.info("number={}", dat.toString());
            log.info("num={}", dat.getNum());
        });
        Thread t2 = new Thread(() -> {
            //dat.setNumber(3);
            //dat.setNum(3);

            log.info("number={}", dat.getNumber());
            log.info("num={}", dat.getNum());
        });

        t1.start();
        t2.start();

        log.info("Finished!");
    }
}

class Data {
    private Integer num;

    private ThreadLocal<Integer> number = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return 1;
        }
    };

    public synchronized void setNumber(int val) {
        number.set(val);
    }

    public void setNum(int val) {
        num = val;
    }

    @Override
    public String toString() {
        return number.get().toString();
    }

    public Integer getNum() {
        return num;
    }

    public Integer getNumber() {
        return number.get();
    }
}
