package com.Key.spring.jdbcTemplate.dao.impl;

import com.Key.spring.jdbcTemplate.bean.Admin;
import com.Key.spring.jdbcTemplate.dao.inter.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * dao实现类
 *
 * @author Key
 * @date 2021/09/04/15:40
 **/
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {

    @Autowired
    private JdbcTemplate jt;

    /**
     * 添加一条记录
     * @param admin 添加的管理员对象
     */
    @Override
    public void addInfo(Admin admin) {
        // 创建SQL语句
        String sql = "insert into admin value(null, ?, ?)";

        // 创建对象数组，填充占位符
        Object[] args = {admin.getAdminName(), admin.getPassword()};

        // 调用jt的方法，具体实现操作数据库的逻辑
        int update = jt.update(sql, args);

        System.out.println(update);
    }

    /**
     * 通过id查询单挑记录
     * @param id id值
     * @return 返回单个对象
     */
    @Override
    public Admin queryForId(int id) {
        // 创建SQL语句
        String sql = "select * from admin where id = ?";

        // 调用jt 的方法，操作数据库
        Admin admin = jt.queryForObject(sql, new BeanPropertyRowMapper<>(Admin.class), id);

        return admin;
    }

    /**
     * 查询多条记录
     * @return 返回集合
     */
    @Override
    public List<Admin> queryList() {
        // 创建SQL语句
        String sql = "select * from admin";

        // 调用jt 的方法，操作数据库
        List<Admin> admins = jt.query(sql, new BeanPropertyRowMapper<>(Admin.class));

        return admins;
    }

    /**
     * 批量添加数据
     * @param batchArgs 添加数据集合
     */
    @Override
    public void batchAddInfo(List<Object[]> batchArgs) {
        // 创建SQL语句
        String sql = "insert into admin value(null, ?, ?)";

        // 调用jt 的方法，操作数据库
        int[] ints = jt.batchUpdate(sql, batchArgs);

        System.out.println(Arrays.toString(ints));
    }
}
