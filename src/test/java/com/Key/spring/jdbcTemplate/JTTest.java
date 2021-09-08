package com.Key.spring.jdbcTemplate;

import com.Key.spring.jdbcTemplate.bean.Admin;
import com.Key.spring.jdbcTemplate.service.impl.AdminServiceImpl;
import com.Key.spring.jdbcTemplate.service.inter.AdminService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 *
 * @author Key
 * @date 2021/09/04/15:56
 **/
public class JTTest {

    private static final AdminService adminService;

    static {
        // 创建工厂对象
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate.xml");

        // 获取service 对象
        adminService = context.getBean(AdminServiceImpl.class);
    }

    /**
     * 测试添加记录
     */
    @Test
    public void testAdd() {

        // 创建要添加的对象
        Admin admin = new Admin();
        admin.setAdminName("刘唐");
        admin.setPassword("2w34");

        // 调用service 的方法
        adminService.addInfo(admin);
    }

    /**
     * 测试通过id查询单个对象
     */
    @Test
    public void testQueryForId() {
        // 直接调用service的方法
        Admin admin = adminService.queryForId(4);

        System.out.println(admin);
    }

    /**
     * 测试查询多个记录，集合形式返回
     */
    @Test
    public void testQueryList() {
        // 调用service的方法
        List<Admin> admins = adminService.queryList();

        // 打印集合
        admins.forEach(System.out::println);
    }

    /**
     * 测试批量添加
     */
    @Test
    public void testBatchAdd() {
        // 创建数据集合
        List<Object[]> batchInfo = new ArrayList<>();

        Object[] o1 = {"晁盖", "999"};
        Object[] o2 = {"鲁智深", "110"};
        Object[] o3 = {"林冲", "av"};

        batchInfo.add(o1);
        batchInfo.add(o2);
        batchInfo.add(o3);

        // 调用service方法
        adminService.batchAddInfo(batchInfo);
    }
}
