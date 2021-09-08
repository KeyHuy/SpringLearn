package com.Key.spring.aop;

import com.Key.spring.aop.bean.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AOP测试类
 *
 * @author Key
 * @date 2021/09/02/19:32
 **/
public class aopTest {

    @Test
    public void testProxy() {
        // 获取工厂对象
        ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");

        // 通过工厂对象获取book对象
        Book book = context.getBean("book", Book.class);

        book.add();
    }
}
