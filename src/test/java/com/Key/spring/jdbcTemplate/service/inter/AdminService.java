package com.Key.spring.jdbcTemplate.service.inter;

import com.Key.spring.jdbcTemplate.bean.Admin;

import java.util.List;

/**
 * 管理员Service接口
 *
 * @author Key
 * @date 2021/09/04/15:44
 **/
public interface AdminService {

    /**
     * 添加一条记录
     */
    void addInfo(Admin admin);

    /**
     * 通过id 查询单个记录
     * @param id id值
     * @return 返回对象
     */
    Admin queryForId(int id);

    /**
     * 查询多条记录
     * @return 返回集合
     */
    List<Admin> queryList();

    /**
     * 批量添加数据
     * @param batchArgs 添加数据集合
     */
    void batchAddInfo(List<Object[]> batchArgs);
}