package com.caijinfu.utcool.bean;

/**
 * RobolectricBean
 *
 * @author 猿小蔡
 * @date 2022/7/8
 */
public class RobolectricBean {

    private String name;

    private int color;

    public RobolectricBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
