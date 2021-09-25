package com.Key.spring.aop.proxy;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 增强类2.0
 *  - 用于测试增强类的优先级
 *
 * @author Key
 * @date 2021/09/25/11:06
 **/
@Component
@Aspect
@Order(0)
public class UserProxy {
    /**
     * 抽取公共切入点
     */
    @Pointcut(value = "execution(* com.Key.spring.aop.bean.Book.add(..))")
    public void pointCut() {
        // code...
    }

    /**
     * 前置通知
     */
    @Before(value = "pointCut()")
    public void before() {
        System.out.println("UserProxy --> 前置通知");
    }
}
