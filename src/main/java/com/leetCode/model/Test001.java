package com.leetCode.model;

/**
 * Created by Hachel on 2018/3/20
 */
public class Test001 {
    private int age;
    public Test001(){
        this.age = 12;
    }

    public Test001 getEntity(){
        return this;
    }

    public int setAndGet(int age){
        this.age = age;
        return age;
    }
}
