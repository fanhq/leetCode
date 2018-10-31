package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Hachel on 2018/4/26
 */
public class ZKClient {

    public static void main(String[] args) throws Exception {
        //start();

        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();

            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/data/path", "hello".getBytes());

            byte[] data = client.getData().forPath("/data/path");
            System.out.println(new String(data));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
