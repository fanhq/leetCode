package com.leetCode.problems;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    public static void main(String[] args) {
        Map<String,String> map = new LinkedHashMap<String, String>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");



        map.get("2");
        for (Map.Entry entry : map.entrySet()){
            System.out.println(entry.getValue());
        }
    }

}
