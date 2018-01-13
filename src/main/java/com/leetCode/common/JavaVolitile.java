package com.leetCode.common;

import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/13
 */
public class JavaVolitile {


    public static void main(String[] args) {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                while (myCommon.flag){
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(myCommon.flag);
                }
            }
        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myCommon.flag = false;
    }

}
