package com.leetCode.executor;

import com.google.common.util.concurrent.*;

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
            try {
                TimeUnit.SECONDS.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "success";
        }
    }

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        RunableClass runableClass = new RunableClass();
        System.out.println("submit runable");
        executor.submit(runableClass);

        CallableClass callableClass = new CallableClass();
        System.out.println("submit callable");
        Future<String> future = executor.submit(callableClass);

        FutureTask<String> futureTask = new FutureTask<>(callableClass);
        System.out.println("submit futureTask");
        executor.submit(futureTask);
        try {
            System.out.println(future.get());
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 使用guava提供的MoreExecutors工具类包装原始的线程池
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);
        //向线程池中提交一个任务后，将会返回一个可监听的Future，该Future由Guava框架提供
        ListenableFuture<String> lf = listeningExecutor.submit(callableClass);
        //添加回调，回调由executor中的线程触发，但也可以指定一个新的线程
        Futures.addCallback(lf, new FutureCallback<String>() {

            //耗时任务执行失败后回调该方法
            @Override
            public void onFailure(Throwable t) {
                System.out.println("on failure" + t.getMessage());
            }

            //耗时任务执行成功后回调该方法
            @Override
            public void onSuccess(String s) {
                System.out.println("on success " + s);
            }
        }, executor);

        executor.shutdown();
    }

}
