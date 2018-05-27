package com.leetCode;


import com.leetCode.common.MyTest;
import com.leetCode.common.RunAbleClass;

import java.util.concurrent.*;
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
        ExecutorService executor = new ThreadPoolExecutor(4, 8, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        try {
            MyTest myTest = new MyTest("hello");
            RunAbleClass runAbleClass = new RunAbleClass(myTest);
            executor.submit(runAbleClass);

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    myTest.sayHi();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
