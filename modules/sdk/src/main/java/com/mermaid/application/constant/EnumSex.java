package com.mermaid.application.constant;

/**
 * Desription:
 * 性别枚举
 * @author:Hui CreateDate:2018/8/31 0:19
 * version 1.0
 */
public enum EnumSex {

    /**
     * 男人
     */
    MAN(1),

    /**
     * 女人
     */
    WOMAN(0),
    ;
    private Integer value;

    EnumSex(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
