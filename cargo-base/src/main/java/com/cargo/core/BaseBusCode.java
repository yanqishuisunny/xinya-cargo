package com.cargo.core;

import com.commom.core.IBusCode;

/**
 * 请求响应码枚举
 * Created by lether on 2016/7/28.
 */
public enum BaseBusCode implements IBusCode {

    /**
     * "1000","成功"
     */
    SUCCESS(1000,"成功"),
    /**
     * "1001","业务失败"
     */
    FAIL(1001,"业务失败"),

    /**
     * "1003","无效token"
     */
    TOKEN_INVALID(1002,"无效token"),

    /**
     * "1005","token丢失"
     */
    TOKEN_LOSE(1003,"token丢失"),

    /**
     * "1004","token过期"
     */
    TOKEN_EXPIRATION(1004,"token过期"),

    /**
     * "1002","权限不足"
     */
    PERMISSION_DENIED(1005,"权限不足"),

    /**
     * 您当前的权限已发生变化，请重新登录
     */
    PERMISSION_CHANGE(1111,"您当前的权限已发生变化，请重新登录"),

    /**
     * "1006","签名无效"
     */
    INVALID_SIGNATURE(1006,"签名无效"),

    /**
     * "1007","参数缺失"
     */
    PARAMETER_BIND_ERROR(1007,"参数缺失"),
    /**
     * "1008","参数格式错误"
     */
    PARAMETER_VALID_ERROR(1008,"参数验证失败"),

    /**
     * "1100","重复提交"
     */
    REPEAT_SUBMIT(1100,"重复提交"),

    /**
     * "2001","无效的资源"
     */
    ILLEGALARGUMENT(2001,"无效的资源"),

    /**
     * "2002","资源被关联"
     */
    ASSOCIATIVE(2002,"资源被关联"),

    /**
     *  无效请求地址
     */
    NOT_FOUND(2003,"无效请求地址"),
    /**
     * 无效的枚举类型
     */
    INVALID_ENUM(2004,"无效的枚举类型"),

    /**
     * 违反唯一约束
     */
    UNIQUE_ERROR(2004,"违反唯一约束"),

    /**
     * 企业信息未审核通过，不能进行此操作!
     */
    AUDIT_NOT_SUCCESS(2006,"企业信息未审核通过，不能进行此操作!"),

    OTHER_LOGIN_ERROR(5000,"已在其他设备登录"),
    /**
     * "9999","服务器异常"
     */
    SYS_EXP(9999,"服务器异常"),

    /**
     * "2002","用户已经被禁用"
     */
    USER_TO_VOID(2002,"用户已经被禁用"),

    /**
     * "2004","缺少入参"
     */
    MISSING_INPUT_PARAMETERS(2004,"缺少入参");



    private int code;
    private String message;


    BaseBusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 方法描述: 枚举转换
     *
     * @param code code
     * @return BusCode BusCode
     */
    public static BaseBusCode parseOf(int code) {
        for (BaseBusCode item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public static BaseBusCode parseOf(String key) {
        return BaseBusCode.parseOf(Integer.parseInt(key));
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
