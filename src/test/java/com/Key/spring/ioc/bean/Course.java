package com.Key.spring.ioc.bean;

/**
 * 课程实体类
 *
 * @author Key
 * @date 2021/08/17/22:02
 **/
public class Course {
    private String cname;

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cname='" + cname + '\'' +
                '}';
    }
}
