package com.Key.spring.ioc.bean;

/**
 * 部门实体类
 *
 * @author Key
 * @date 2021/08/17/20:28
 **/
public class Department {
    private String deptName;

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "depName='" + deptName + '\'' +
                '}';
    }
}
