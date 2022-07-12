package com.caijinfu.utcool.bean;

/**
 * Person
 *
 * @author 猿小蔡
 * @date 2022/7/8
 */
public class Person {

    public String name;

    public int sex;

    public int age;

    public Person(String name) {
        this.name = name;
    }

    public Person() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String eat(String food) {
        return food;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", sex=" + sex + ", age=" + age + '}';
    }
}
