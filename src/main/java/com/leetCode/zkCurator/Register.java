package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.omg.CORBA.ServiceDetail;

import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/23
 */
public class Register {

    public static void main(String[] args) {
        try {

            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            client.blockUntilConnected();

            /**
             * 指定服务的 地址，端口，名称
             */
            ServiceInstanceBuilder<ServiceDetail> sib = ServiceInstance.builder();
            sib.address("10.19.13.47");
            sib.port(8888);
            sib.name("iop");
            sib.payload(new ServiceDetail());

            ServiceInstance<ServiceDetail> instance = sib.build();

            ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                    .client(client)
                    .serializer(new JsonInstanceSerializer<ServiceDetail>(ServiceDetail.class))
                    .basePath("/base")
                    .build();
            //服务注册
            serviceDiscovery.registerService(instance);
            serviceDiscovery.start();

            TimeUnit.SECONDS.sleep(70);

            serviceDiscovery.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
