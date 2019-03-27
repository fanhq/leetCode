package com.fanhq.example.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author fanhaiqiu
 * @date 2019/03/27
 */
@Component

public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     *
     * @param beanName
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String beanName) throws BeansException {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     *
     * @param beanClass
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> beanClass) throws BeansException {
        return applicationContext.getBean(beanClass);
    }
}
