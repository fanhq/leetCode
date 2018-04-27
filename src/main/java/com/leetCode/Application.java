package com.leetCode;


import com.leetCode.util.HttpUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    private static volatile boolean falg = true;

    private static ReentrantLock lock = new ReentrantLock();

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(1);
                    System.out.println(HttpUtil.doGet("http://127.0.0.1:8080/demo/hello?publicKey=sdfwe3"));
                }
            });


            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(2);
                    System.out.println(HttpUtil.doGet("http://127.0.0.1:8080/demo/hello?publicKey=sdfwe3"));
                }
            });

            thread.start();
            thread1.start();

            TimeUnit.SECONDS.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
