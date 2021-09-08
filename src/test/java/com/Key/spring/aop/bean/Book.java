package com.Key.spring.aop.bean;

import org.springframework.stereotype.Component;

/**
 * 书的实体类
 *
 * @author Key
 * @date 2021/09/02/19:35
 **/
@Component
public class Book {

    /**
     * 被增强的方法
     */
    public void add() {
        System.out.println("被增强的方法");
    }

}
