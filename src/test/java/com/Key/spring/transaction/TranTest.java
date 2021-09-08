package com.Key.spring.transaction;

import com.Key.spring.transaction.config.TxConfig;
import com.Key.spring.transaction.service.inter.CustomService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 事务测试类
 *
 * @author Key
 * @date 2021/09/06/21:37
 **/
public class TranTest {

    /**
     * 测试使用事务的转账操作
     */
    @Test
    public void testUpdateMoney() {
        // 创建工厂对象
        ApplicationContext context = new ClassPathXmlApplicationContext("transaction.xml");

        // 获取service
        CustomService cService = context.getBean("cService", CustomService.class);

        // 调用service方法
        cService.updateMoney();
    }

    /**
     * 测试完全注解开发下实现事务管理
     */
    @Test
    public void testAnnotationTx() {
        // 加载配置类，创建工厂对象
        ApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);

        // 获取service对象
        CustomService cService = context.getBean("cService", CustomService.class);

        // 调用service方法
        cService.updateMoney();
    }
}
