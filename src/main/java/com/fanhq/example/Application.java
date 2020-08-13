package com.fanhq.example;


import com.fanhq.example.model.Test001;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {
    public static void main(String[] args) throws Exception {
        Test001 test001 = new Test001();
        test001.increment();
        System.out.println(test001.getNum());
    }
}