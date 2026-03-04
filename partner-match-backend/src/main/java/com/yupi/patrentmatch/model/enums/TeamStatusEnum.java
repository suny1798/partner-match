package com.yupi.patrentmatch.model.enums;

public enum TeamStatusEnum {

    PUBLIC("公开", 0),
    PRIVATE("私有", 1),
    SECRET("加密", 2);


    private String text;

    private int value;


    TeamStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

/**
 * 根据给定的整数值获取对应的TeamStatusEnum枚举实例
 * @param value 要匹配的整数值
 * @return 匹配到的TeamStatusEnum枚举实例，如果没有匹配则返回null
 */
    public static TeamStatusEnum getEnumByValue(Integer value) {
    // 如果传入的value为null，直接返回null
        if (value == null){
            return null;
        }
    // 遍历TeamStatusEnum的所有枚举值
        for (TeamStatusEnum statusEnum : TeamStatusEnum.values()) {
        // 查找与给定value值匹配的枚举项
            if (statusEnum.getValue() == value)
            // 找到匹配项后立即返回
                return statusEnum;
        }
    // 如果没有找到匹配的枚举项，返回null
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
