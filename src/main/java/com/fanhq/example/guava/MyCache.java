package com.fanhq.example.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/3/12
 */
public class MyCache {

    private static Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    public static void main(String[] args) {
        try {
            cache.put("a", "1");
            cache.put("a", "2");
            System.out.println(cache.getIfPresent("b"));
            while (true){
                try {
                    System.out.println(cache.get("a", new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            return null;
                        }
                    }));
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
