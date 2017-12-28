package com.leetCode.problems;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hachel on 2017/12/27
 */
public class Application {


    public static void main(String[] args){
//        String a = new String("111333");
//        int h = a.hashCode();
//        System.out.println(h/(h>>>16));
//        System.out.println(8>>>1);
//        System.out.println(h);
//        System.out.println((h = a.hashCode()) ^ (h >>> 16));
//        System.out.println(h & (16-1));
        Map map = new HashMap();
        for (int i =0;i<35;i++){
            map.put(i,i);
        }
    }

}
