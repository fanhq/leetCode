package com.leetCode;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {


    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        List<String> b = a;

        a.add("1");
        b.add("2");

    }

}
