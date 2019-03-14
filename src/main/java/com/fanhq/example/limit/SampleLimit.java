package com.fanhq.example.limit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fanhaiqiu
 * @date 2019/3/14
 */
public class SampleLimit {

    private static final int LIMIT_NUM = 1000;

    private AtomicLong atomicLong = new AtomicLong(0);

    /**
     * 也可以用redis做缓存达到同样的效果
     */
    private LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build(new CacheLoader<>() {
                @Override
                public AtomicLong load(Long aLong) throws Exception {
                    return new AtomicLong(0);
                }

            });

    /**
     * 限流1
     */
    public void sample01() {
        try {
            if (atomicLong.incrementAndGet() > LIMIT_NUM) {
                //拒绝请求
            }
            //处理请求
        } finally {
            atomicLong.decrementAndGet();
        }
    }

    /**
     * 限流2
     */
    public void sample02() {
        try {
            long currentSeconds = System.currentTimeMillis() / 1000;
            if (counter.get(currentSeconds).incrementAndGet() > LIMIT_NUM) {
                //拒绝请求
            }
            //处理请求
        } catch (Exception e) {

        }
    }

    /**
     * 限流3
     */
    public void sample03() {
        try {
            //每秒增加五个令牌
            RateLimiter limiter = RateLimiter.create(5);
            if (limiter.tryAcquire()) {
                //处理请求
            }
        } catch (Exception e) {

        }
    }
}
