package com.Key.spring.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Properties;

/**
 * 完全注解开发实现事务管理
 *
 * @author Key
 * @date 2021/09/08/17:00
 **/
@Configuration
@ComponentScan(basePackages = {"com.Key.spring.transaction"})
@EnableTransactionManagement
public class TxConfig {

    /**
     * get()方法——创建数据库连接池对象
     * @return 返回对象
     */
    @Bean
    public DruidDataSource getDruidDataSource() {
        // 先new 一个对象
        DruidDataSource ds = new DruidDataSource();

        // 引入外部属性文件
        Properties pro = new Properties();
        try {
            pro.load(TxConfig.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 注入外部属性文件中的属性值
        ds.setDriverClassName(pro.getProperty("prop.driverClass"));
        ds.setUrl(pro.getProperty("prop.url"));
        ds.setUsername(pro.getProperty("prop.username"));
        ds.setPassword(pro.getProperty("prop.password"));

        return ds;
    }

    /**
     * get()方法——创建jdbcTemplate对象
     * @param ds 传入数据库连接池对象
     * @return 返回对象
     */
    @Bean
    public JdbcTemplate getJdbcTemplate(DruidDataSource ds) {
        // 先new 一个对象
        JdbcTemplate jt = new JdbcTemplate();

        // 注入dataSource 属性
        jt.setDataSource(ds);

        return jt;
    }

    /**
     * get()方法——创建事务管理器对象
     * @param ds 传入数据库连接池对象
     * @return 返回对象
     */
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DruidDataSource ds) {
        // 先new 一个对象
        DataSourceTransactionManager tManager = new DataSourceTransactionManager();

        // 注入dataSource 属性
        tManager.setDataSource(ds);

        return tManager;
    }
}
