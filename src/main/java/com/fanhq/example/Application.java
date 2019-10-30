package com.fanhq.example;


/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    private static ThreadLocal<String> t1 = new ThreadLocal<>();
    private static ThreadLocal<String> t2 = new ThreadLocal<>();
    static {
        t1.set("t1");
        t2.set("t2");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(t1.get());
        System.out.println(t1.get());
        System.out.println(t2.get());
        System.out.println(t2.get());
    }


}