package com.leetCode;


import reactor.core.publisher.Flux;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    private static volatile boolean falg = true;

    private static ReentrantLock lock = new ReentrantLock();

    private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(128);


    private static ExecutorService executor = new ThreadPoolExecutor(4, 8, 0l, TimeUnit.MILLISECONDS, queue);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Flux.just("Hello", "World").subscribe(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
