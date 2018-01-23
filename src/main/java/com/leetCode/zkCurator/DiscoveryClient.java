package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.omg.CORBA.ServiceDetail;

import java.util.Collection;

/**
 * Created by Hachel on 2018/1/23
 */
public class DiscoveryClient {


    public static void main(String[] args) {

        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();
            client.blockUntilConnected();

            ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                    .client(client)
                    .basePath("/ai/obc/")
                    .build();
            serviceDiscovery.start();

            //根据名称获取服务
            Collection<ServiceInstance<ServiceDetail>> services = serviceDiscovery.queryForInstances("iop");
            for(ServiceInstance<ServiceDetail> service : services) {
                System.out.println(service.getPayload());
                System.out.println(service.getAddress() + "\t" + service.getPort());
                System.out.println("---------------------");
            }

            serviceDiscovery.close();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
