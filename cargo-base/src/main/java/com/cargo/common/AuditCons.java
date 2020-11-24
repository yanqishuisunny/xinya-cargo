package com.cargo.common;

import com.commom.cache.CachePre;
import com.commom.core.IBusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AuditCons {


    /**
     * 审核状态
     */
    @Getter
    @AllArgsConstructor
    enum AuditStatus {

        /**
         * 草稿
         */
        DRAFT(0, "草稿"),
        /**
         * 待审
         */
        WAIT(1, "待审"),
        /**
         * 通过
         */
        APPROVE(2, "通过"),
        /**
         * 拒绝
         */
        REFUSE(3, "拒绝");

        private final Integer value;

        private final String comment;

    }

    /**
     * 审核状态
     */
    @Getter
    @AllArgsConstructor
    enum AuditType {

        ORG(0, "企业"),
        PERSON(1, "个人"),
        DRIVER(2, "司机"),
        VEHICLE(3, "车辆");

        private final Integer value;

        private final String comment;


        public static AuditType get(int i) {
            for (AuditType value : values()) {
                if (value.getValue() == i) {
                    return value;
                }
            }
            return null;
        }
    }


    /**
     * t_audit_record 表中审核状态 的枚举
     */
    @Getter
    @AllArgsConstructor
    enum AuditStatusEnum {
        AUDIT_STATUS_ENUM_REFUSE(1, "已禁用"),

        AUDIT_STATUS_ENUM_PASS(2, "审核通过"),

        AUDIT_STATUS_ENUM_CANCEL(3, "被拒绝");
        private final Integer value;

        private final String comment;

        public static AuditStatusEnum get(int i) {
            for (AuditStatusEnum value : values()) {
                if (value.getValue() == i) {
                    return value;
                }
            }
            return null;
        }
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
        private final String msg;
    }


    enum Constant {

    }

}


