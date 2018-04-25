package com.leetCode;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    private static volatile boolean falg = true;

    private static ReentrantLock lock = new ReentrantLock();

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            ClassLoader classLoader = Application.class.getClassLoader();
            while (true){
                System.out.println(classLoader.toString());
                classLoader = classLoader.getParent();
                if (classLoader == null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
