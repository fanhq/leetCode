package com.leetCode;


/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    public static void main(String[] args) {

        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupted();
        System.out.println(Thread.currentThread().isInterrupted());

        System.out.println("end");

    }

}
