package com.fanhq.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Hachel on 2018/11/19
 */
@Component
public class EventPbulish {
    @Autowired
    ApplicationContext context;

    public void publish(String message) {
        context.publishEvent(new EventTest(this, message));
    }
}
