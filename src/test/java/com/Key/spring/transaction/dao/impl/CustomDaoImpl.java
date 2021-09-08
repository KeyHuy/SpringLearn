package com.Key.spring.transaction.dao.impl;

import com.Key.spring.transaction.dao.inter.CustomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 客户dao实现类
 *
 * @author Key
 * @date 2021/09/06/21:23
 **/
@Repository("cDao")
public class CustomDaoImpl implements CustomDao {

    @Autowired
    private JdbcTemplate jt;

    /**
     * 修改客户金额
     * @param changedMoney 改变的金额
     * @param cName 客户名
     */
    @Override
    public void updateMoney(int changedMoney, String cName) {
        // 创建SQl语句
        String sql = "update customs set money = money + ? where name = ?";

        // 调用jt的方法，操作数据库
        int update = jt.update(sql, changedMoney, cName);

        System.out.println(update);
    }
}
