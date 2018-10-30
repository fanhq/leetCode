package com.leetCode.common;

/**
 * Created by Hachel on 2018/5/27
 */
public class MyTest {

    public String name = new String("jane");

    private String mark;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public MyTest(){
    }

    public MyTest(String mark){
        this.mark = mark;
    }

    public void sayHi(){
        System.out.println(this.mark);
    }

    public String sayHello(String record){
        return record;
    }
}
