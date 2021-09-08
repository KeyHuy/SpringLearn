package com.Key.spring.ioc.bean;


/**
 * 用户实体类
 *
 * @author Key
 * @date 2021/08/17/10:00
 **/
public class User {
    private String name;
    private int age;

    public User(String username, int age) {
        this.name = username;
        this.age = age;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
