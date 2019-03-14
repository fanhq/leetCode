package com.fanhq.example.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by Hachel on 2018/11/19
 */
@Component
public class ApplicationEventPublisherUtil implements ApplicationEventPublisherAware {


    private static ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }

    public static ApplicationEventPublisher getPublisher() {
        return publisher;
    }

    public static void setPublisher(ApplicationEventPublisher publisher) {
        ApplicationEventPublisherUtil.publisher = publisher;
    }


}
