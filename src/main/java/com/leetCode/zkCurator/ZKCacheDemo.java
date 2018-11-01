package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by Hachel on 2018/10/31
 */
public class ZKCacheDemo {

    private static final String PATH_CACHE = "/example/pathCache";

    private static final String NODE_CACHE = "/example/nodeCache";

    private static final String TREE_CACHE = "/example/treeCache";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();

        //PathChildrenCache
        System.out.println("=========================PathChildrenCache==========================");
        PathChildrenCache pCache = new PathChildrenCache(client, PATH_CACHE, true);
        pCache.start();
        pCache.getListenable().addListener((c, e) -> {
            System.out.println("事件类型：" + e.getType());
            if (null != e.getData()) {
                System.out.println("节点数据：" + e.getData().getPath() + " = " + new String(e.getData().getData()));
            }
        });
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test01", "01".getBytes());
        Thread.sleep(100);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test02", "02".getBytes());
        Thread.sleep(100);
        client.setData().forPath("/example/pathCache/test01", "01_V2".getBytes());
        Thread.sleep(100);
        for (ChildData data : pCache.getCurrentData()) {
            System.out.println("getCurrentData:" + data.getPath() + " = " + new String(data.getData()));
        }
        client.delete().forPath("/example/pathCache/test01");
        Thread.sleep(100);
        client.delete().forPath("/example/pathCache/test02");
        Thread.sleep(2000);
        pCache.close();

        //Node Cache
        System.out.println("=========================NodeCache==========================");
        client.create().creatingParentsIfNeeded().forPath(NODE_CACHE);
        NodeCache nCache = new NodeCache(client, NODE_CACHE);
        nCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData data = nCache.getCurrentData();
                if (null != data) {
                    System.out.println("节点数据：" + new String(nCache.getCurrentData().getData()));
                } else {
                    System.out.println("节点被删除!");
                }
            }
        });
        nCache.start();
        client.setData().forPath(NODE_CACHE, "01".getBytes());
        Thread.sleep(100);
        client.setData().forPath(NODE_CACHE, "02".getBytes());
        Thread.sleep(100);
        client.delete().deletingChildrenIfNeeded().forPath(NODE_CACHE);
        Thread.sleep(2000);
        nCache.close();

        //Tree cache
        System.out.println("=========================TreeCache==========================");
        client.create().creatingParentsIfNeeded().forPath(TREE_CACHE);
        TreeCache cache = new TreeCache(client, TREE_CACHE);
        cache.getListenable().addListener((c, e) ->
                System.out.println("事件类型：" + e.getType() + " | 路径：" + (null != e.getData() ? e.getData().getPath() : null)));
        cache.start();
        client.setData().forPath(TREE_CACHE, "01".getBytes());
        Thread.sleep(100);
        client.setData().forPath(TREE_CACHE, "02".getBytes());
        Thread.sleep(100);
        client.delete().deletingChildrenIfNeeded().forPath(TREE_CACHE);
        Thread.sleep(1000 * 2);
        cache.close();

        client.close();

    }
}
