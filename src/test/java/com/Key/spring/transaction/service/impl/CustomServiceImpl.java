package com.Key.spring.transaction.service.impl;

import com.Key.spring.transaction.dao.inter.CustomDao;
import com.Key.spring.transaction.service.inter.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户service实现类
 *
 * @author Key
 * @date 2021/09/06/21:26
 **/
@Service("cService")
@Transactional
public class CustomServiceImpl implements CustomService {

    @Autowired
    private CustomDao cDao;

    /**
     * 修改客户金额
     */
    @Override
    public void updateMoney() {
        // decade向faiz转100块
        cDao.updateMoney(-100, "decade");

        // faiz账户中增加100块
        cDao.updateMoney(100, "faiz");
    }
}
