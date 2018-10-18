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

    public MyTest(String mark){
        this.mark = mark;
    }

    public void sayHi(){
        System.out.println(this.mark);
    }

}
