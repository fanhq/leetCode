package com.leetCode.common;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/4/1
 */
public class Test2{

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        try {
            lock.lock();
            BufferedReader osr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\temp file\\text.txt")), "UTF-8"));
            while (true){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(osr.readLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
