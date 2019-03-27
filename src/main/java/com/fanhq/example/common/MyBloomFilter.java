package com.fanhq.example.common;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author fanhaiqiu
 * @date 2019/3/27
 */
public class MyBloomFilter {

    public static void main(String[] args) {
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                500,
                0.01);
        filter.put(1);
        filter.put(2);
        filter.put(3);
        System.out.println(filter.mightContain(1));
    }
}
