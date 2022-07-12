package com.caijinfu.utcool.shadow;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import com.caijinfu.utcool.bean.Person;

/**
 * 创建{@link Person}的影子类
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
@Implements(Person.class)
public class PersonShadow {

    /**
     * 通过@RealObject注解可以访问原始对象，但注意，通过@RealObject注解的变量调用方法，依然会调用Shadow类的方法，而不是原始类的方法 只能用来访问原始类的field
     */
    @RealObject
    private Person realBean;

    /**
     * 需要一个无参构造方法
     */
    public PersonShadow() {}

    /**
     * 对应原始类的有参构造方法，必须保持参数一致
     */
    public void __constructor__(String name) {
        realBean.name = name;
    }

    /**
     * 可以选择覆写某个方法
     */
    @Implementation
    public int getAge() {
        return 18;
    }

    @Implementation
    public String getName() {
        return "小明";
    }

    @Implementation
    public int getSex() {
        return 1;
    }

    @Implementation
    public void setName(String name) {
        realBean.name = name;
    }

    @Implementation
    public void setSex(int sex) {
        realBean.sex = sex;
    }

    @Implementation
    public void setAge(int age) {
        realBean.age = age;
    }

    /**
     * 可以在原始类基础上添加而外的支持方法
     */
    public String descripOrigan() {
        return realBean.name + "," + realBean.sex + "," + realBean.age;
    }
}
