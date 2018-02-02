package com.leetCode;


/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    private final static char DEFAULT_SPLIT_KEY = 1;

    public static void main(String[] args) {
        String msgs = "10|13999999999|msg|";
        String[] s = msgs.split("\\|");
        System.out.println(s.length);
    }

}
