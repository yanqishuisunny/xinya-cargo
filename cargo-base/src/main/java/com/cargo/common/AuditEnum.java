package com.cargo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 赵恒亮
 * @author
 *   自动审核的枚举类
 *
 *
 */
@Getter
@AllArgsConstructor
public enum AuditEnum {



//    AUDIT_ENUM_ONE(1,"单位开票信息以及联系人填写"),
    AUDIT_ENUM_ONE(1,"联系人及联系电话填写"),
    AUDIT_ENUM_TWO(2,"企业授权书文件上传"),
    AUDIT_ENUM_THREE(3,"营业执照信息校验"),
    AUDIT_ENUM_FOUR(4,"法人身份证信息校验"),
    AUDIT_ENUM_FIVE(5,"法人身份证与营业执照一致性"),
    AUDIT_ENUM_SIX(6,"道路经营许可证信息校验"),
    AUDIT_ENUM_SEVEN(7,"道路运输经营许可证与营业执照企业名称一致性"),
    AUDIT_ENUM_EIGHT(8,"危险化学品经营许可证信息上传"),

   // AUDIT_ENUM_NINE(9,"危险化学品经营许可证与营业执照企业名称一致性"),
    AUDIT_ENUM_TEN(10,"营业执照已被注册"),

    AUDIT_ENUM_PERSON_ONE(14,"人脸识别已通过"),
    AUDIT_ENUM_PERSON_TWO(15,"身份证信息校验"),
    AUDIT_ENUM_PERSON_THREE(16,"人脸识别与身份证信息一致性"),


    AUDIT_ENUM_DRIVER_THREE(17,"驾驶证信息校验"),
    AUDIT_ENUM_DRIVER_FOUR(18,"身份证信息与驾驶证信息一致性"),


    AUDIT_ENUM_VEHICLE_ONE(19,"行驶证信息校验"),
    AUDIT_ENUM_VEHICLE_TWO(20,"车辆道路运输证信息校验"),
    AUDIT_ENUM_VEHICLE_THREE(21,"行驶证与车辆道路运输证信息一致性"),
    AUDIT_ENUM_VEHICLE_FOUR(22,"行驶证信息提交情况"),

    AUDIT_ENUM_SAFE(23,"安全生产许可证上传"),

    AUDIT_ENUM_SUCCEED(11,"成功"),
    AUDIT_ENUM_DEFEATED(12,"失败"),
    AUDIT_ENUM_NULL(13,"为空值")







    ;


    public static AuditEnum getEnum(Integer integer){
        for (AuditEnum auditEnum:AuditEnum.values()) {
             if(auditEnum.getType().equals(integer)){
                   return auditEnum;
             }
        }
        return null;
    }


    private Integer type;
    private String message;
}
