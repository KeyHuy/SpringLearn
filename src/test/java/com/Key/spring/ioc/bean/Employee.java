package com.Key.spring.ioc.bean;

/**
 * 员工实体类
 *
 * @author Key
 * @date 2021/08/17/20:27
 **/
public class Employee {
    private String empName;
    private String gender;
    private Department empDept;

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmpDept(Department empDept) {
        this.empDept = empDept;
    }

    public Department getEmpDept() {
        return empDept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", empDept=" + empDept +
                '}';
    }
}
