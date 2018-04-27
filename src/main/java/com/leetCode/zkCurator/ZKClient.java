package com.leetCode.zkCurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Hachel on 2018/4/26
 */
public class ZKClient {
    private static final String PATH = "/mq_monitor";
   // static CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.27.55", new ExponentialBackoffRetry(1000, 3));
    //static PathChildrenCache pathChildrenCache = new PathChildrenCache(client, PATH, true);

    public static void main(String[] args) throws Exception {
        //start();

        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.start();

            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/data/path1", "hello".getBytes());

            byte[] data = client.getData().forPath("/");
            System.out.println(new String(data));

//            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/iop/rpc", true);
//            List<ChildData> data = pathChildrenCache.getCurrentData();
//            for (ChildData datum : data) {
//                System.out.println(datum.getPath() + " = " + new String(datum.getData()));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void start() {
//        try {
//            client.start();
//            pathChildrenCache.start();
//            addPathChildListener(pathChildrenCache);
//            processCommands(client, pathChildrenCache);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void shutdown() {
//        CloseableUtils.closeQuietly(pathChildrenCache);
//        CloseableUtils.closeQuietly(client);
//    }
//
//    private static void addPathChildListener(PathChildrenCache cache) {
//        // a PathChildrenCacheListener is optional. Here, it's used just to log changes
//        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
//                String path = ZKPaths.getNodeFromPath(event.getData().getPath());
//                byte[] data = event.getData().getData();
//                String dataStr = new String(data);
//                switch (event.getType()) {
//                    case CHILD_ADDED: {
//                        System.out.println("Node added: " + path + " data " + dataStr);
//                        break;
//                    }
//                    case CHILD_UPDATED: {
//                        System.out.println("Node changed: " + path + " data " + dataStr);
//                        break;
//                    }
//                    case CHILD_REMOVED: {
//                        System.out.println("Node removed: " + path + " data " + dataStr);
//                        break;
//                    }
//                }
//            }
//        };
//        cache.getListenable().addListener(listener);
//    }
//
//    private static void processCommands(CuratorFramework client, PathChildrenCache cache) throws Exception {
//        printHelp();
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//            boolean done = false;
//            while (!done) {
//                System.out.print("> ");
//                String line = in.readLine();
//                if (line == null) {
//                    break;
//                }
//                String command = line.trim();
//                String[] parts = command.split("\\s");
//                if (parts.length == 0) {
//                    continue;
//                }
//                String operation = parts[0];
//                String args[] = Arrays.copyOfRange(parts, 1, parts.length);
//
//                if (operation.equalsIgnoreCase("help") || operation.equalsIgnoreCase("?")) {
//                    printHelp();
//                } else if (operation.equalsIgnoreCase("q") || operation.equalsIgnoreCase("quit")) {
//                    done = true;
//                } else if (operation.equals("set")) {
//                    setValue(client, command, args);
//                } else if (operation.equals("remove")) {
//                    remove(client, command, args);
//                } else if (operation.equals("list")) {
//                    list(cache);
//                }
//
//                Thread.sleep(1000); // just to allow the console output to catch up
//            }
//        } finally {
//
//        }
//    }
//
//    private static void list(PathChildrenCache cache) {
//        if (cache.getCurrentData().size() == 0) {
//            System.out.println("* empty *");
//        } else {
//            for (ChildData data : cache.getCurrentData()) {
//                System.out.println(data.getPath() + " = " + new String(data.getData()));
//            }
//        }
//    }
//
//    private static void remove(CuratorFramework client, String command, String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("syntax error (expected remove <path>): " + command);
//            return;
//        }
//
//        String name = args[0];
//        String path = PATH + "/" + name;
//        try {
//            client.delete().forPath(path);
//            client.sync();
//        } catch (KeeperException.NoNodeException e) {
//            // ignore
//        }
//    }
//
//    private static void setValue(CuratorFramework client, String command, String[] args) throws Exception {
//        if (args.length != 2) {
//            System.err.println("syntax error (expected set <path> <value>): " + command);
//            return;
//        }
//
//        String name = args[0];
////        if (name.contains("/")) {
////            System.err.println("Invalid node name" + name);
////            return;
////        }
//        //String path = ZKPaths.makePath(PATH, name);
//        String path = PATH + "/" + name;
//
//        byte[] bytes = args[1].getBytes();
//        try {
//            if (client.checkExists().forPath(path) != null) {
//                client.setData().forPath(path, bytes);
//            } else {
//                client.create().creatingParentsIfNeeded().forPath(path, bytes);
//            }
//
//        } catch (KeeperException.NoNodeException e) {
//            //client.create().creatingParentsIfNeeded().forPath(path, bytes);
//        }
//    }
//
//    private static void printHelp() {
//        System.out.println("An example of using PathChildrenCache. This example is driven by entering commands at the prompt:\n");
//        System.out.println("set <name> <value>: Adds or updates a node with the given name");
//        System.out.println("remove <name>: Deletes the node with the given name");
//        System.out.println("list: List the nodes/values in the cache");
//        System.out.println("quit: Quit the example");
//        System.out.println();
//    }
}
