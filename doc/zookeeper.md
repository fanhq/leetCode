#####zookeeper客户端 - Curator
+ Curator简介  
  Apache Curator is a Java/JVM client library for Apache ZooKeeper, a distributed coordination service. It includes a highlevel API framework and utilities to make using Apache ZooKeeper much easier and more reliable. It also includes recipes for common use cases and extensions such as service discovery

+ Curator常用api  
  * 创建客户端  
    ```
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.builder()
                                .connectString("127.0.0.1:2181")
                                .sessionTimeoutMs(5000)
                                .connectionTimeoutMs(5000)
                                .retryPolicy(retryPolicy)
                                .build();
    client.start();
    ```
  * 创建节点数据  
    ```//创建节点
       //PERSISTENT：持久化 默认模式
       //PERSISTENT_SEQUENTIAL：持久化并且带序列号
       //EPHEMERAL：临时
       //EPHEMERAL_SEQUENTIAL：临时并且带序列号
       //创建节点并递归创建父节点，并指定创建模式
       client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/data/path", "hello".getBytes());
    ```
  * 删除节点数据  
    ```
    client.delete().deletingChildrenIfNeeded().forPath("/data/path");
    ```
  * 更新节点数据
    ```
     client.setData().forPath("/data/path", "world".getBytes());
    ```
  * 查询节点数据
    ```
    byte[] data = client.getData().forPath("/data/path");//获取指定节点数据
    List<String> childs = client.getChildren().forPath("/")//获取子节点
    ```
+ Curator事件(cache)</br> 

  ZooKeeper原生支持通过注册Watcher来进行事件监听，但是其使用并不是特别方便，需要开发人员自己反复注册Watcher，比较繁琐。Curator引入了Cache来实现对ZooKeeper服务端事件的监听。Cache是Curator中对事件监听的包装，其对事件的监听其实可以近似看作是一个本地缓存视图和远程ZooKeeper视图的对比过程。同时Curator能够自动为开发人员处理反复注册监听，从而大大简化了原生API开发的繁琐过程
  
  * 事件监听示例代码
    ```
    
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
    ```
   * 控制台输出展示
    ```
   =========================PathChildrenCache==========================
   事件类型：CONNECTION_RECONNECTED
   事件类型：CHILD_ADDED
   节点数据：/example/pathCache/test01 = 01
   事件类型：CHILD_ADDED
   节点数据：/example/pathCache/test02 = 02
   事件类型：CHILD_UPDATED
   节点数据：/example/pathCache/test01 = 01_V2
   getCurrentData:/example/pathCache/test01 = 01_V2
   getCurrentData:/example/pathCache/test02 = 02
   事件类型：CHILD_REMOVED
   节点数据：/example/pathCache/test01 = 01_V2
   事件类型：CHILD_REMOVED
   节点数据：/example/pathCache/test02 = 02
   =========================NodeCache==========================
   节点数据：01
   节点数据：02
   节点被删除!
   =========================TreeCache==========================
   事件类型：NODE_ADDED | 路径：/example/treeCache
   事件类型：INITIALIZED | 路径：null
   事件类型：NODE_UPDATED | 路径：/example/treeCache
   事件类型：NODE_UPDATED | 路径：/example/treeCache
   事件类型：NODE_REMOVED | 路径：/example/treeCache

    ```