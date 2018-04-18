package com.leetCode.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/4/1
 */
public class Test1 {

    private static ReentrantLock lock = new ReentrantLock();

    private Test1(){

    }

    public static void main(String[] args) {

        lock.lock();
        try {
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\temp file\\text.txt"), true), "UTF-8"));
            while (true){
                TimeUnit.SECONDS.sleep(1);
                //写文件
                osw.write("dfdfd");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
