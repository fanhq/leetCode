package com.fanhq.example.spring;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Hachel on 2018/11/19
 */
public class EventTest extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String message;

    public EventTest(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}