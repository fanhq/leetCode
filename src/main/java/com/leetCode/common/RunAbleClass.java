package com.leetCode.common;

/**
 * Created by Hachel on 2018/5/27
 */
public class RunAbleClass implements Runnable {

    private MyTest myTest;

    @Override
    public void run() {
        myTest.sayHi();
    }

    public RunAbleClass(MyTest myTest){
        this.myTest = myTest;
    }
}
