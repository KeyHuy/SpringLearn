package com.Key.spring.transaction.dao.inter;

/**
 * 客户类dao接口
 *
 * @author Key
 * @date 2021/09/06/21:19
 **/
public interface CustomDao {

    /**
     * 更新客户账户的钱
     * @param changedMoney 改变的金额
     * @param cName 客户名
     */
    void updateMoney(int changedMoney, String cName);
}