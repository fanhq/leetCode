package com.fanhq.example.executor;

import java.util.concurrent.CompletableFuture;
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
                        Thread.sleep(500);
                        System.out.println("end.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            CompletableFuture.runAsync(() -> {
                System.out.println("runAsync");
            }, executor).exceptionally(e -> {
                e.printStackTrace();
                throw new RuntimeException();
            });

            //如果lamda表达式只有一行,可以不写return, 不加花括号{}, 返回值后面可以不加分号
            CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 100, executor);
            CompletableFuture<Integer> future2 = future1.thenApplyAsync(i -> {
                System.out.println(i + 100);
                return i + 200;
            }, executor).exceptionally(e -> {
                e.printStackTrace();
                return 400;
            });
            //接受一个参数，无返回结果 CompletableFuture
            future2.thenAccept(integer -> {
                System.out.println(500);
            });
            System.out.println(future1.get());
            System.out.println(future2.get());
            //必须要先shutdown，再调用awaitTermination
            executor.shutdown();
            boolean isTermination = executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(isTermination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
