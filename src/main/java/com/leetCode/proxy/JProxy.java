package com.leetCode.proxy;

import com.leetCode.Application;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Hachel on 2018/10/30
 */
public class JProxy {

    public static void main(String[] args) {
        //jdk
        try {
            MyIface proxy = (MyIface) Proxy.newProxyInstance(Application.class.getClassLoader(), new Class[]{MyIface.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println(method.getName());
                    System.out.println(method.getParameterTypes());
                    System.out.println(args[0]);
                    return "i am fine";
                }
            });

            Object result = proxy.sayHi("hello world");
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //cglib
        try {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MyIface.class);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    System.out.println(method.getName());
                    System.out.println(method.getParameterTypes());
                    System.out.println(objects[0]);
                    return "i am fine";
                }
            });

            MyIface proxy = (MyIface)enhancer.create();
            Object result = proxy.sayHi("hello world");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
