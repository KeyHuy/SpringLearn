package com.Key.spring.transaction.entity;

/**
 * 客户实体类
 *
 * @author Key
 * @date 2021/09/06/21:13
 **/
public class Customs {
    private Integer id;
    private String name;
    private Integer money;

    public Customs() {
    }

    public Customs(Integer id, String name, Integer money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Customs{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
