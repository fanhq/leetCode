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

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            FutureTask futureTask = new FutureTask(new Test());
            Thread thread = new Thread(futureTask);
            thread.start();
            System.out.println("begin");
            System.out.println(futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class Test implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            try {
                TimeUnit.SECONDS.sleep(10);
            }catch (Exception e){

            }
            return 10000;
        }
    }

}
