package com.cargo.common;

import com.commom.core.IBusCode;
import lombok.AllArgsConstructor;

/**
 * 请求响应码枚举
 * Created by lether on 2016/7/28.
 */
@AllArgsConstructor
public enum RoleBusCode implements IBusCode {



    ROLE_CODE_REPEAT(1,"角色代码重复"),
    ROLE_NAME_REPEAT(2,"角色名称重复"),
    ROLE_DELETE_FAL(3,"删除角色存在用户关联关系"),

    login_check_err_11(11,"用户名无效，请重新输入"),
    login_check_err_12(12,"该账号已经被禁用"),
    login_check_err_13(13,"密码输入错误超限制，请20分钟后重试！"),
    login_check_err_14(14,"密码输入错误"),

    user_check_err_21(21,"该用户不能被删除"),
    user_check_err_22(22,"删除用户不存在"),
    user_check_err_23(22,"该用户名已存在"),
    user_check_err_24(22,"该用户名不存在"),

    org_check_err_31(31,"改组织下存在子节点"),
    org_check_err_32(32,"已有用户或单据关联，不能进行删除"),

    org_check_err_33(33,"用户角色不存在"),
    user_check_err_34(22,"该企业超级管理员已经存在"),
    user_check_err_35(22,"该企业下该用户名已存在");

    private int code;
    private String message;



    /**
     * 方法描述: 枚举转换
     *
     * @param code code
     * @return BusCode BusCode
     */
    public static RoleBusCode parseOf(int code) {
        for (RoleBusCode item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public static RoleBusCode parseOf(String key) {
        return RoleBusCode.parseOf(Integer.parseInt(key));
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "BusCode{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                '}';
    }}
