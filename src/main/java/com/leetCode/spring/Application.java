package com.leetCode.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Hachel on 2018/11/19
 */
public class Application {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
//        EventPbulish eventPbulish = context.getBean(EventPbulish.class);
//        eventPbulish.publish("zhangsan");
//        ApplicationEventPublisherUtil.getPublisher().publishEvent(new EventTest(context, "hello"));
//
//        context.close();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.leetCode.spring");
    }
}
