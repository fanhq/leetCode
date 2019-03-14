package com.fanhq.example.spring;

import com.fanhq.example.model.Test002;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by Hachel on 2018/12/7
 */
@Configuration
@Order(1)
public class ConfigTwo {

    @Bean
    @Qualifier(value = "test0011")
    public Test002 test002(){
        System.out.println("config two");
        return new Test002();
    }
}
