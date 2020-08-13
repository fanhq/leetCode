package com.fanhq.example.model;

import java.io.Serializable;

/**
 * Created by Hachel on 2018/3/20
 */
public class Test001 implements Serializable{
    private String age;
    private String sex;
    private String name;

    private int num;

    private String value;
    private boolean succes;

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
