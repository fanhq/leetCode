package com.leetCode.zkCurator;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by Hachel on 2018/2/27
 */
public class ZKConnect {


    public static void main(String[] args) {
        //String connectionString = "127.0.0.1:2181";
        String connectionString = "10.1.241.103:2181";
        int sessionTimeout = 30000;
        try {
            ZooKeeper zk = new ZooKeeper(connectionString, sessionTimeout, null);
            List<String> children = zk.getChildren("/iop", new Watcher() {
                public void process(WatchedEvent event) {
                    System.out.println("this is children node event");
                    System.out.println(event);
                }
            });
            for (String s : children) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
