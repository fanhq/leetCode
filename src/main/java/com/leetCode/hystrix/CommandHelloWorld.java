package com.leetCode.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author fanhaiqiu
 * @date 2019/2/14
 */
public class CommandHelloWorld  extends HystrixCommand<String> {
    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here
        return "Hello " + name + "!";
    }

    public static void main(String[] args) {
        CommandHelloWorld helloWorld = new CommandHelloWorld("World");
        String s = helloWorld.execute();
        System.out.println(s);
    }
}
