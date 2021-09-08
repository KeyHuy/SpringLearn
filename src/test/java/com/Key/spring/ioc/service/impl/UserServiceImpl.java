package com.Key.spring.ioc.service.impl;

import com.Key.spring.ioc.dao.UserDao;
import com.Key.spring.ioc.service.UserService;

/**
 * 用户Service实现类
 *
 * @author Key
 * @date 2021/08/17/15:15
 **/
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao uDao) {
        this.userDao = uDao;
    }

    @Override
    public void update() {
        userDao.update();
        System.out.println("service层的update方法...");
    }
}
