package com.leetCode.spring;

import com.leetCode.common.MyTest;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

/**
 * Created by Hachel on 2018/10/30
 */
public class CglibReflect {

    public static void main(String[] args) throws Exception{

        MyTest myTest = new MyTest();
        FastClass serviceFastClass = FastClass.create(MyTest.class);
        FastMethod serviceFastMethod = serviceFastClass.getMethod("sayHello", new Class[]{String.class});

        Object result = serviceFastMethod.invoke(myTest, new Object[]{"hello"});

        System.out.println(result);
    }
}
