package com.caijinfu.utcool.shadow;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import com.caijinfu.utcool.bean.RobolectricBean;

/**
 * 创建{@link RobolectricBean}的影子类
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
@Implements(RobolectricBean.class)
public class RobolectricBeanShadow {

    /**
     * 通过@RealObject注解可以访问原始对象，但注意，通过@RealObject注解的变量调用方法，依然会调用Shadow类的方法，而不是原始类的方法 只能用来访问原始类的field
     */
    @RealObject
    private RobolectricBean realBean;

    /**
     * 需要一个无参构造方法
     */
    public RobolectricBeanShadow() {

    }

    /**
     * 对应原始类的构造方法
     *
     * @param name
     *            对应原始类构造方法的传入参数
     */
    public void __constructor__(String name) {
        realBean.setName(name);
    }

    /**
     * 原始对象的方法被调用的时候，Robolectric会根据方法签名查找对应的Shadow方法并调用
     */
    @Implementation
    public String getName() {
        return "Hello, I ma shadow of RobolectricBean: " + realBean.getName();
    }

    @Implementation
    public int getColor() {
        return realBean.getColor();
    }

    @Implementation
    public void setColor(int color) {
        realBean.setColor(color);
    }
}