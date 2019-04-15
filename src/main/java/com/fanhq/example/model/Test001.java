package com.fanhq.example.model;

import jdk.jfr.Name;

import java.io.Serializable;

/**
 * Created by Hachel on 2018/3/20
 */
public class Test001 implements Serializable{
    private String age;
    private String sex;
    private String name;

    @Name("turnon")
    public void sayHi(Serializable hi){
        System.out.println(hi);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
