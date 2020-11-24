package com.commom.cache;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 梁建军
 * 创建日期： 2019/12/5
 * 创建时间： 16:17
 * @version 1.0
 * @since 1.0
 */
@Getter
public enum RoleEnum {

    /**
     * 生产商
     */
    PRODUCER("生产商"),
    /**
     * 贸易商
     */
    TRADER("贸易商"),

    /**
     * 仓库
     */
    WAREHOUSE("仓储商"),
    /**
     * 承运商
     */
    CARRIER("承运商"),
    ;

    /**
     * 代码
     */
    private final long code;
    /**
     * 名称
     */
    private final String name;

    RoleEnum(String name) {
        this.name = name;
        this.code = 1 << ordinal();
    }

    /**
     * 被包含
     *
     * @param codes 包含集合
     * @return true 包含在内
     */
    public boolean beContains(long codes) {
        return (codes & getCode()) == getCode();
    }

    /**
     * 获取单个角色
     *
     * @param code 代码
     * @return 角色枚举
     */
    public static RoleEnum get(long code) {
        for (RoleEnum value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    /**
     * 多个角色
     *
     * @param codes 代码
     * @return 角色枚举
     */
    public static RoleEnum[] gets(long codes) {
        List<RoleEnum> roleEnums = new ArrayList<>(values().length);
        for (RoleEnum value : values()) {
            if ((value.getCode() & codes) == value.getCode()) {
                roleEnums.add(value);
            }

        }
        return roleEnums.toArray(new RoleEnum[0]);
    }

    public static void main(String[] args) {
        RoleEnum[] arr = gets(11);
        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i].name);
        }
    }

    /**
     * 获取角色的枚举
     *
     * @param roleEnums 角色列表
     * @return 角色代码
     */
    public static long get(RoleEnum... roleEnums) {
        if (roleEnums == null || roleEnums.length == 0) {
            return 0;
        }
        long temp = 0;
        for (RoleEnum roleEnum : roleEnums) {
            temp |= roleEnum.getCode();
        }
        return temp;
    }
}
