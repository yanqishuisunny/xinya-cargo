package com.cargo.common;

import com.commom.cache.CachePre;
import com.commom.core.IBusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UserEnum{

    @Getter
    @AllArgsConstructor
    enum Code implements IBusCode {

        /**
         * 账号或密码错误
         */
        LOGIN_ACCOUNT_ERROR(1, "登录名或密码错误"),

        /**
         * 验证码错误
         */
        LOGIN_CAPTHA_ERROR(2, "验证码错误"),

        /**
         * 账号被禁用
         */
        LOGIN_USER_DISABLED(3, "账号被禁用"),

        /**
         * 密码输入错误超限制，请20分钟后重试！
         */
        LOGIN_USER_PWD_LIMIT(4, "密码输入错误超限制，请5分钟后重试"),

        /**
         * 用户手机号码不合法
         */
        LOGIN_USER_TEL_ERROR(5, "用户手机号码不合法"),
        /**
         * 短信发送失败
         */
        SEED_MESSAGE_ERROR(6, "短信发送失败"),
        /**
         * 手机号已经存在
         */
        REG_PHONE_DUPLICATION_ERROR(8, "手机号已经存在!"),
        /**
         * 验证码错误 请重新输入
         */
        LOGIN_MESSAGE_CAPTHA_ERROR(7, "验证码错误 请重新输入"),

        /**
         * 手机号不存在
         */
        LOGIN_PHONE_NULL_ERROR(9, "手机号不存在!"),

        /**
         * 短信已发送，请勿重复
         */
        SEED_MESSAGE_LIMIT_ERROR(10, "短信已发送，请勿重复"),
        IDCARD_REPEAT_ERROR(11, "身份证号已存在！"),
        IDCARD_OVER_ERROR(12, "已完成实名认证！"),
        ACCOUNT_DOUBLE_ERROR(13, "账号已存在！"),
        SMS_LIMIT(14, "验证码发送过于频繁，请稍后再试"),
        SMS_DAY_OUT(14, "今天获取的验证码已经超过上限，请您明日再试"),
        ID_CARD_CHECK(15, "人证校验不通过！"),
        ICARD_CHECK_LIMIT(16, "实名认证失败次数过多，如有疑问可联系客服！"),
        SMS_TOKEN_EXPIRATION(18, "提交失败，验证码已过期，请在10分钟内完成注册"),
        ERROR(99, "占用"),
        /**
         * 密码输入错误超限制，请5分钟后重试！
         */
        LOGIN_USER_PWD_FIVE(5, "该帐号已被锁定,请5分钟后再试,或点击忘记密码找回"),

        LOGIN_USER_PWD_THREE(3, "用户名密码连续3次错误，再错2次您的帐号将被锁定5分钟"),

        USERNAME_NULL(13, "账号必填！"),

        PASSWORD_NULL(13, "密码必填！"),

        PHONENO_NULL(13, "电话号码必填！"),

        NOT_USER(13, "没有此用户！"),

        IS_CANCELLATION(19, "该账户已经注销,不能登录！"),

        SMS_REGISTER(19, "该账号已经注册，不可重复注册！"),

        SMS_CANCELLATION(19, "该账号已经注销，不可注册！"),

        NOT_REGISTER(8, "当前手机号暂未注册，请先完成注册！"),

        USER_ALREADY_REGISTER(19, "该手机号已是欣物盟的注册用户，请勿重复注册，如需帮助请联系客服"),

        APP_LOGIN_LIMIT(-1, "该手机号暂时不能登录欣物盟网页版，请前往APP操作"),

        APP_MODIFY_PASSWORD(19, "该手机号是欣物盟APP的注册账户，请前往APP操作"),

        NEW_PC_LOGIN_LIMIT(-1, "该账号用于登录欣物盟PC端，请前往登录"),

        PC_LOGIN_ROLE(19, "该手机号暂时不能登录欣物盟网APP，请至www.ail365.com欣物盟平台网页版操作，或联系平台客服"),

        APP_CONSIGNOR_LOGIN_ERROR(19, "该账号是欣物盟车主版 APP 的注册用户，请前往车主 版 APP 操作"),

        USER_SHUTDOWN(19, "此账户已关闭，不能登录！"),

        APP_SHIPPER(19, "该账号是欣物盟车主版 APP 的注册用户，请前往车主 版 APP 操作"),

        ORG_DELETRE(19, "此账户对应企业已经被删除，不能登录！"),


        ;

        private final int code;

        private final String message;

    }

    @Getter
    @AllArgsConstructor
    enum SMSType {
        /**
         * 登录验证码
         */
        LOGIN(CachePre.LOING_SMS_PHONE, "登录验证码"),
        /**
         * 忘记密码验证码
         */
        FORGET(CachePre.LOING_SMS_FORGET, "忘记密码验证码"),
        /**
         * 注册验证码
         */
        REGISTER(CachePre.LOING_SMS_REGISTER, "注册验证码"),
        /**
         * 微信收货人/发货人验证码
         */
        SENDER_CONSIGNEE(CachePre.LOING_SMS_SENDER_CONSIGNEE, "微信收货人/发货人验证码");

        private final String rediskey;
        private final String message;
    }


    enum Constant {

    }


}


