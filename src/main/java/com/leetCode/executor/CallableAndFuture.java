package com.leetCode.executor;

import java.util.concurrent.*;

/**
 * Created by Hachel on 2018/3/8
 */
public class CallableAndFuture {

    static class RunableClass implements Runnable {
        @Override
        public void run() {
            System.out.println("runable thread");
        }
    }

    /**
     * Callable结合ExecutorService使用
     */
    static class CallableClass implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("callable thread");
            return "success";
        }
    }

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        RunableClass runableClass = new RunableClass();
        executor.submit(runableClass);

        CallableClass callableClass = new CallableClass();
        Future<String> future = executor.submit(callableClass);

        FutureTask<String> futureTask = new FutureTask<>(callableClass);
        executor.submit(futureTask);
        try {
            System.out.println(future.get());
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}
