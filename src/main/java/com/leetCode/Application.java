package com.leetCode;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;
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
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client =
                    CuratorFrameworkFactory.builder()
                            .connectString("127.0.0.1:2181")
                            .sessionTimeoutMs(5000)
                            .connectionTimeoutMs(5000)
                            .authorization("digest", "fanhq:123456".getBytes())
                            .retryPolicy(retryPolicy)
                            .build();
            client.start();
            //client.delete().deletingChildrenIfNeeded().forPath("/dubbo");
            getRecusion(client, "/dubbo");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void getRecusion(CuratorFramework client, String path) {
        try {
            byte[] value = client.getData().forPath(path);
            if (value != null) {
                System.out.println(path + ":" + new String(value));
            }
            List<String> childs = client.getChildren().forPath(path);
            if (childs != null) {
                for (String child : childs) {
                    String newPath = path.equals("/") ? path + child : path + "/" + child;
                    getRecusion(client, newPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
