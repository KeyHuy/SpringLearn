package com.Key.spring.aop.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 书籍类的增强类
 *
 * @author Key
 * @date 2021/09/02/19:47
 **/
@Component
@Aspect
public class BookProxy {

    /**
     * 抽取公共切入点
     */
    @Pointcut(value = "execution(* com.Key.spring.aop.bean.Book.add(..))")
    public void pointCut() {
        // code...
    }

    /**
     * 1. 前置通知
     */
    @Before(value = "pointCut()")
    public void before() {
        System.out.println("前置通知");
    }

    /**
     * 2. 后置通知（返回通知）
     */
    @AfterReturning(value = "pointCut()")
    public void afterReturning() {
        System.out.println("后置通知");
    }

    /**
     * 3. 环绕通知
     * @param proceedingJoinPoint 用于调用被增强的方法
     */
    @Around(value = "pointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知——方法前执行...");

        // 执行被增强的方法
        proceedingJoinPoint.proceed();

        System.out.println("环绕通知——方法后执行...");
    }

    /**
     * 4. 异常通知
     */
    @AfterThrowing(value = "pointCut()")
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    /**
     * 5. 最终通知
     */
    @After(value = "pointCut()")
    public void after() {
        System.out.println("最终通知");
    }
}
