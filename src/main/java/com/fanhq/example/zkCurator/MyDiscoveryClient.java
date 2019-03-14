package com.fanhq.example.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/23
 */
public class MyDiscoveryClient {

    public static final String BASE_PATH = "services";
    public static final String SERVICE_NAME = "HelloService";

    public static void main(String[] args) {

        CuratorFramework client = null;
        MyServiceDiscovery serviceDiscovery = null;
        try {
            client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();

            serviceDiscovery = new MyServiceDiscovery(client, MyDiscoveryClient.BASE_PATH);   //服务发现
            serviceDiscovery.start();

            for (int i = 0; i < 10; i++) {

                ServiceInstance<ServerPayload> instance = serviceDiscovery.getServiceProvider(MyDiscoveryClient.SERVICE_NAME);

                System.out.println("service:" + MyDiscoveryClient.SERVICE_NAME + " instance id:" + instance.getId() +
                        ", name:" + instance.getName() + ", address:" + instance.getAddress() + ", port:" + instance.getPort());

                TimeUnit.SECONDS.sleep(3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serviceDiscovery != null) {
                try {
                    serviceDiscovery.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.close();
        }
    }

}
