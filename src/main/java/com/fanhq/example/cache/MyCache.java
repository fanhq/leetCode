package com.fanhq.example.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/3/12
 */
public class MyCache {

    /**
     * guava 堆内缓存
     */
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(1)
            .recordStats()
            .build();

    /**
     * mapdb 对内缓存 可以创建堆外缓存，需要配置jvm参数 -XX:MaxDirectMemorySize=10G
     */
    private static HTreeMap mapHeap = DBMaker.heapDB().concurrencyScale(16)
            .make().hashMap("mycache")
            .expireMaxSize(1024)
            .expireAfterCreate(10, TimeUnit.SECONDS)
            .expireAfterUpdate(10, TimeUnit.SECONDS)
            .expireAfterGet(10, TimeUnit.SECONDS).create();



    public static void main(String[] args) {
        try {
            cache.put("a", "1");
            cache.put("b", "2");
            System.out.println(cache.getIfPresent("b"));
            while (true) {
                try {
                    System.out.println(cache.get("a", new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            return null;
                        }
                    }));
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
