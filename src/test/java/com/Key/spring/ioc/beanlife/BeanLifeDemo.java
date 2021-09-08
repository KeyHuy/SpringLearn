package com.Key.spring.ioc.beanlife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

/**
 * Bean生命周期演示
 *
 * @author Key
 * @date 2021/08/20/10:43
 **/
public class BeanLifeDemo implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
        EnvironmentAware, ResourceLoaderAware, ApplicationEventPublisherAware, ApplicationContextAware {

/*+---------------------------------------实例化阶段---------------------------------------------+*/

    public BeanLifeDemo() {
        System.out.println("一、实例化 --> 执行无参构造器...");
    }


/*+---------------------------------------注入属性阶段---------------------------------------------+*/
    private String name;
    private int age;
    public void setName(String name) {
        System.out.println("二、注入属性 --> 2.1 执行setName()");
        this.name = name;
    }
    public void setAge(int age) {
        System.out.println("二、注入属性 --> 2.2 执行setAge()");
        this.age = age;
    }
    @Override
    public String toString() {
        return "BeanLifeDemo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


/*+---------------------------------------实现各个aware 接口---------------------------------------------+*/

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("三、实现aware 接口 --> 3.2 对应BeanClassLoaderAware接口");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("三、实现aware 接口 --> 3.3 对应BeanFactoryAware接口");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("三、实现aware 接口 --> 3.1 对应BeanNameAware接口");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("三、实现aware 接口 --> 3.7 对应ApplicationContextAware接口");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("三、实现aware 接口 --> 3.6 对应ApplicationEventPublisherAware接口");
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("三、实现aware 接口 --> 3.4 对应EnvironmentAware接口");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        System.out.println("三、实现aware 接口 --> 3.5 对应ResourceLoaderAware接口");
    }


/*+---------------------------------------初始化阶段---------------------------------------------+*/

    /**
     * 自定义初始化方法
     *  - 基于xml文件：在<bean>中添加 init-method 属性，属性值为方法名
     *  - 基于注解：在方法上添加注解 @PostConstruct
     */
    public void initMethod() {
        System.out.println("五、初始化 --> 5.2 自定义初始化方法");
    }

    /**
     * 实现Java 接口内部的初始化方法
     *  - 实现 InitializingBean 接口
     *  - 不用配置和添加注解
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("五、初始化 --> 5.1 Java接口内部的初始化方法");
    }


/*+---------------------------------------销毁阶段---------------------------------------------+*/
    /**
     * 自定义销毁方法
     *  - 基于xml文件：在<bean>中添加 destroy-method 属性，属性值为方法名
     *  - 基于注解：在方法上添加注解 @PreDestroy
     */
    public void destroyMethod() {
        System.out.println("八、销毁 --> 8.2 自定义销毁方法");
    }

    /**
     * 实现Java 接口内部的销毁方法
     *  - 实现 DisposableBean 接口
     *  - 不用配置和添加注解
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("八、销毁 --> 8.1 Java接口内部的销毁方法");
    }
}
