package com.leetCode.zkCurator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Hachel on 2018/4/26
 */
public class ZKClient {

    public static void main(String[] args) throws Exception {

        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client =
                    CuratorFrameworkFactory.builder()
                            .connectString("127.0.0.1")
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
            System.out.println(new String(data));
            //检查数据是否存在 返回等于null则不存在
            client.checkExists().forPath("/data/path");
            //删除数据
            client.delete().deletingChildrenIfNeeded().forPath("/data/path");
            data = client.getData().forPath("/data/path");
            System.out.println(new String(data));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
