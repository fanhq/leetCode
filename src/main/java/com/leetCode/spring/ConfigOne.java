package com.leetCode.spring;

import com.leetCode.model.Test001;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by Hachel on 2018/12/7
 */

@Configuration
@Order(2)
public class ConfigOne {

    @Bean
    public Test001 test001(){
        System.out.println("config one");
        return new Test001();
    }
}
