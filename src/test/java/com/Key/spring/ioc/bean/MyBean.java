package com.Key.spring.ioc.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 自定义的工厂类，实现FactoryBean
 *
 * @author Key
 * @date 2021/08/18/10:33
 **/
public class MyBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        // 直接创建User对象并返回
        return new User("曹操", 43);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
