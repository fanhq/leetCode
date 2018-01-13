package com.leetCode.common;

import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/13
 */
public class JavaVolitile {

    public static volatile boolean flag = true;

    public static void main(String[] args) {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                int i = 1;
                while (flag){
                    i++;
                }
                System.out.println(i);
            }
        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
    }

}
