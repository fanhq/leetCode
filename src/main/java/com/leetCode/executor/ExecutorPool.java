package com.leetCode.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/29
 */
public class ExecutorPool {

    public static void main(String[] args) {
        try {
            LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 1000, TimeUnit.SECONDS, blockingQueue);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("begin.....");
                        Thread.sleep(5000);
                        System.out.println("end.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            //必须要先shutdown，再调用awaitTermination
            executor.shutdown();
            boolean isTermination = executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(isTermination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
