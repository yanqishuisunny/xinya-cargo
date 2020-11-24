package com.cargo.common;

import com.commom.core.IBusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author GF
 * @Date 2019-7-30 11:12:29
 * @Description 企业校验字段
 **/
public interface OrgEnum{

    @Getter
    @AllArgsConstructor
    enum Code implements IBusCode {

        /**
         * 组织名称已存在你
         */
        ORGNAME_EXITS_ERROR(1, "组织名称已存在你"),

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
        LOGIN_USER_PWD_LIMIT(4, "密码输入错误超限制，请20分钟后重试！"),

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
         * 角色申请错误
         */
        ORG_ROLE_ERROR(10, "角色申请错误!"),

        ERROR(99, "占用");

        private final int code;

        private final String message;
    }


    @Getter
    @AllArgsConstructor
    enum OrgCheckEnum {

        /**
         * 营业执照-企业名称
         */
        CKECK_ENTERPRISE_ORG_NAME("orgName","org_name,license_org_name","营业执照-企业名称"),

        /**
         * 营业执照-社会统一信用代码
         */
        CKECK_ENTERPRISE_ORG_CODE("orgCode","license_org_code","营业执照-社会统一信用代码"),

        /**
         * 道路运输许可证-许可号
         */
        CKECK_ENTERPRISE_ORG_NO("orgNo","road_trans_no","道路运输许可证-许可号"),

        CKECK_ENTERPRISE_ORG_NO_NAMA("orgNo1","road_trans_no","道路运输许可证-许可号"),

        /**
         * 道路运输许可证-业户名称
         */
        CKECK_ENTERPRISE_BUSINESS_NAME("businessName","road_trans_org_name","道路运输许可证-业户名称"),

        /**
         * 危险品证书编号
         */
        CKECK_ENTERPRISE_ERTIFICATE_NUMBER("ertificateNumber","danger_trans_code","危险品证书编号"),

        /**
         * 危险品运输许可证-单位名称
         */
        CKECK_ENTERPRISE_UNIT_NAME("unitName","danger_trans_name","危险品运输许可证-单位名称");

        private final String key;

        private final String code;

        private final String description;

        public static   OrgCheckEnum getOrgCheckEnum(String key){
            for(OrgCheckEnum orgCheckEnum : OrgCheckEnum.values()){
                if(orgCheckEnum.key.equals(key)){
                    return orgCheckEnum;
                }
            }
            return null;
        }
    }


    @Getter
    @AllArgsConstructor
    public enum  OrgChooseStatusEnum {

        Org_Choose_Status_off(0,"未选中"),
        Org_Choose_Status_on(1,"选中");

        private final Integer key;

        private final String description;
    }


    @Getter
    @AllArgsConstructor
    public enum OrgRoleEnum {
        ORG_ROLE_TYPE_0(0,"承运商"),
        ORG_ROLE_TYPE_1(1,"货主"),
        ORG_ROLE_TYPE_2(2,"两者都是");

        private final int type;

        private final String description;
    }

    @Getter
    @AllArgsConstructor
    public enum  OrgSourceEnum {
        ORG_SOURCE_0(0,"注册"),
        ORG_SOURCE_1(1,"创建");

        private final int type;

        private final String description;

    }

    @Getter
    @AllArgsConstructor
    public enum OrgStatusEnum {

        ORG_STATUS_0(0,"待审核"),
        ORG_STATUS_1(1,"审核拒绝"),
        ORG_STATUS_2(2,"审核通过");

        private final int type;

        private final String description;
    }

    @Getter
    @AllArgsConstructor
    public enum OrgTypeEnum {
        ORG_TYPE_0(0,"公司"),

        ORG_TYPE_1(1,"网点");

        private final int type;

        private final String description;

    }

    @Getter
    @AllArgsConstructor
    public enum OrgBizTypeEnum {
        ORG_BIZ_TYPE_0(0,"企业"),

        ORG_BIZ_TYPE_1(1,"个人");

        private final int type;

        private final String description;

    }

    @Getter
    @AllArgsConstructor
    public enum OrgCheckTypeEnum {
        ORG_BIZ_TYPE_0(0,"查看"),

        ORG_BIZ_TYPE_1(1,"创建");

        private final int type;

        private final String description;

    }


}


