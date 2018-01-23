package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/23
 */
public class DiscoveryClient {

    public static final String BASE_PATH = "services";
    public static final String SERVICE_NAME = "HelloService";

    public static void main(String[] args) {

        CuratorFramework client = null;
        ServiceDiscover serviceDiscover = null;
        try {
            client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();

            serviceDiscover = new ServiceDiscover(client, DiscoveryClient.BASE_PATH);   //服务发现
            serviceDiscover.start();

            for (int i = 0; i < 10; i++) {

                ServiceInstance<ServerPayload> instance = serviceDiscover.getServiceProvider(DiscoveryClient.SERVICE_NAME);

                System.out.println("service:" + DiscoveryClient.SERVICE_NAME + " instance id:" + instance.getId() +
                        ", name:" + instance.getName() + ", address:" + instance.getAddress() + ", port:" + instance.getPort());

                TimeUnit.SECONDS.sleep(3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serviceDiscover != null) {
                try {
                    serviceDiscover.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.close();
        }
    }

}
