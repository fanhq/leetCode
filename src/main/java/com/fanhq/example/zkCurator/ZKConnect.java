package com.fanhq.example.zkCurator;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by Hachel on 2018/2/27
 */
public class ZKConnect {


    public static void main(String[] args) {
        String connectionString = "127.0.0.1:2181";
        int sessionTimeout = 30000;
        try {
            ZooKeeper zk = new ZooKeeper(connectionString, sessionTimeout, null);
           // zk.addAuthInfo("digest", ":".getBytes());
            List<String> children = zk.getChildren("/", new Watcher() {
                public void process(WatchedEvent event) {
                    System.out.println("this is children node event");
                    System.out.println(event);
                }
            });
            System.out.println(children.size());
            for (String s : children) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
