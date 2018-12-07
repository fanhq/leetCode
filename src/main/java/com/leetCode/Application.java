package com.leetCode;


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
     * @param argsArrayList
     */
    public static void main(String[] args) {
        try {
            //System.out.println(1<<3|0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
