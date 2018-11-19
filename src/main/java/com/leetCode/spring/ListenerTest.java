package com.leetCode.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by Hachel on 2018/11/19
 */
@Component
public class ListenerTest implements ApplicationListener<EventTest> {
    @Override
    public void onApplicationEvent(EventTest event) {
        System.out.println("onApplicationEvent test:" + event.getMessage());
    }
}
