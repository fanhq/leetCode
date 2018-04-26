package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by Hachel on 2018/4/26
 */
public class ZKClient {

    public static void main(String[] args) {
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
