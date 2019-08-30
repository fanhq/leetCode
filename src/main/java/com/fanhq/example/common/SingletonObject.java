package com.fanhq.example.common;

/**
 * @author fanhaiqiu
 * @date 2019/8/30
 */
public class SingletonObject {
    private SingletonObject() {

    }

    /**
     * 枚举类型是线程安全的，并且只会装载一次
     */
    private enum Singleton {
        INSTANCE;

        private final SingletonObject instance;

        Singleton() {
            instance = new SingletonObject();
        }

        private SingletonObject getInstance() {
            return instance;
        }
    }

    public static SingletonObject getInstance() {

        return Singleton.INSTANCE.getInstance();
    }
}
