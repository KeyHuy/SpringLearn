package com.Key.spring.ioc.dao.impl;

import com.Key.spring.ioc.dao.UserDao;

/**
 * 用户dao实现类
 *
 * @author Key
 * @date 2021/08/17/15:13
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public void update() {
        System.out.println("dao类的update方法...");
    }
}
