package com.leetCode;


import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    private static volatile boolean falg = true;

    private static ReentrantLock lock = new ReentrantLock();

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);


    private static ExecutorService executor = new ThreadPoolExecutor(4, 8, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
