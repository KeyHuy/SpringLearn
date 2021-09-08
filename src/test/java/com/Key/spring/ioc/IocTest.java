package com.Key.spring.ioc;

import com.Key.spring.ioc.bean.Employee;
import com.Key.spring.ioc.bean.Students;
import com.Key.spring.ioc.bean.User;
import com.Key.spring.ioc.beanlife.BeanLifeDemo;
import com.Key.spring.ioc.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试类
 *
 * @author Key
 * @date 2021/08/17/21:01
 **/
public class IocTest {

    /**
     * 测试Bean管理
     *  - 创建对象
     *  - 注入属性
     */
    @Test
    public void testUser() {
        // 1. 创建工厂类对象
        ApplicationContext context = new ClassPathXmlApplicationContext("userBean.xml");

        // 2. 通过工厂类获取对象
        User user = context.getBean("u", User.class);

        // 3. 打印user测试
        System.out.println(user);
    }

    /**
     * 测试外部Bean
     */
    @Test
    public void testExternalBean() {

        // 加载xml文件，获取工厂类对象
        ApplicationContext context = new ClassPathXmlApplicationContext("externalBean.xml");

        // 根据工厂类对象获取service对象
        UserService uService = context.getBean("uService", UserService.class);

        // 调用service类方法，测试
        uService.update();
    }

    /**
     * 测试级联赋值
     */
    @Test
    public void testEmpAndDept() {
        ApplicationContext context = new ClassPathXmlApplicationContext("empAndDeptBean.xml");

        Employee emp = context.getBean("emp", Employee.class);

        System.out.println(emp);
    }

    /**
     * 测试数组、集合类型属性的注入
     */
    @Test
    public void testStu() {
        ApplicationContext context = new ClassPathXmlApplicationContext("stuBean.xml");

        Students stu = context.getBean("stu", Students.class);

        System.out.println(stu);
    }

    /**
     * 测试通过util名称空间注入集合类型属性
     */
    @Test
    public void testStuUtil() {
        ApplicationContext context = new ClassPathXmlApplicationContext("stuUtilBean.xml");

        Students stu = context.getBean("stu", Students.class);

        System.out.println(stu);
    }

    @Test
    public void testFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("factoryBean.xml");

        User myBean = context.getBean("myBean", User.class);

        System.out.println(myBean);
    }

    /**
     * 测试spring Bean的声明周期
     */
    @Test
    public void testBeanLife() {
        // 加载spring配置文件，获取工厂对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanLifeDemo.xml");
        // 获取对象
        BeanLifeDemo lifeDemo = context.getBean("bl", BeanLifeDemo.class);

        System.out.println("七、获取实例化对象 --> " + lifeDemo);

        // 关闭工厂对象，即销毁Bean，spring bean不会自动销毁，必须调用工厂对象的close() 方法
        context.close();
    }
}
