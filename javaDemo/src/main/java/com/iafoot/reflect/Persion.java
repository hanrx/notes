package com.iafoot.reflect;

/**
 * @author ：iAfoot 反射测试类
 * @description：TODO
 * @date ：2021/3/21 11:58
 */
public class Persion {
    private String name;
    public int age;

    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
