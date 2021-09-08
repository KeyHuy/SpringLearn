package com.Key.spring.jdbcTemplate.service.impl;

import com.Key.spring.jdbcTemplate.bean.Admin;
import com.Key.spring.jdbcTemplate.dao.inter.AdminDao;
import com.Key.spring.jdbcTemplate.service.inter.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service实现类
 *
 * @author Key
 * @date 2021/09/04/15:44
 **/
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 添加一条记录
     * @param admin 添加的管理员对象
     */
    @Override
    public void addInfo(Admin admin) {
        // 直接调用dao的方法
        adminDao.addInfo(admin);
    }

    /**
     * 通过id查询单挑记录
     * @param id id值
     * @return 返回单个对象
     */
    @Override
    public Admin queryForId(int id) {
        return adminDao.queryForId(id);
    }

    /**
     * 查询多条记录
     * @return 返回集合
     */
    @Override
    public List<Admin> queryList() {
        return adminDao.queryList();
    }

    /**
     * 批量添加数据
     * @param batchArgs 添加数据集合
     */
    @Override
    public void batchAddInfo(List<Object[]> batchArgs) {
        adminDao.batchAddInfo(batchArgs);
    }
}
