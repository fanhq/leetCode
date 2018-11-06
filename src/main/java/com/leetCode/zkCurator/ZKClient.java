package com.leetCode.zkCurator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Created by Hachel on 2018/4/26
 */
public class ZKClient {

    public static void main(String[] args) throws Exception {

        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client =
                    CuratorFrameworkFactory.builder()
                            .connectString("127.0.0.1:2181")
                            .sessionTimeoutMs(5000)
                            .connectionTimeoutMs(5000)
                            .retryPolicy(retryPolicy)
                            .build();
            client.start();
            //创建节点
            //PERSISTENT：持久化 默认模式
            //PERSISTENT_SEQUENTIAL：持久化并且带序列号
            //EPHEMERAL：临时
            //EPHEMERAL_SEQUENTIAL：临时并且带序列号
            //创建节点并递归创建父节点，并指定创建模式
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/data/path", "hello".getBytes());
            //获取节点数据
            byte[] data = client.getData().forPath("/data/path");
            System.out.println(new String(data));
            //更新数据
            client.setData().forPath("/data/path", "world".getBytes());
            data = client.getData().forPath("/data/path");
            //检查数据是否存在 返回等于null则不存在
            if (client.checkExists().forPath("/data/path") == null) {
                System.out.println("not exist");
            }else {
                System.out.println(new String(data));
            }
            //删除数据
            client.delete().deletingChildrenIfNeeded().forPath("/data/path");
            if (client.checkExists().forPath("/data/path") == null) {
                System.out.println("not exist");
            }else {
                System.out.println(new String(data));
            }
            //获取子节点
            List<String> childs = client.getChildren().forPath("/");
            System.out.println(childs.size());

            //创建zookeeper的客户端
//            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//            CuratorFramework client = CuratorFrameworkFactory.newClient("10.21.41.181:2181,10.21.42.47:2181,10.21.49.252:2181", retryPolicy);
//            client.start();

            //创建分布式锁, 锁空间的根节点路径为/curator/lock
            InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
            mutex.acquire();
            //获得了锁, 进行业务流程
            System.out.println("Enter mutex");
            //完成业务流程, 释放锁
            mutex.release();

            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/curator/lock/");

            System.out.println(client.getChildren().forPath("/curator/lock").size());

            //关闭客户端
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
