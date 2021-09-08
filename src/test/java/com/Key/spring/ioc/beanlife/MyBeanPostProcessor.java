package com.Key.spring.ioc.beanlife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 自定义一个后置处理器
 *  - 实现 BeanPostProcessor接口
 *
 * @author Key
 * @date 2021/08/20/11:06
 **/
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 在Bean 初始化前执行的方法
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("四、将Bean交给后置处理器 --> Bean初始化前执行...");
        return bean;
    }

    /**
     * 在Bean 初始化后执行的方法
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("六、将Bean交给后置处理器 --> Bean初始化后执行...");
        return bean;
    }
}
