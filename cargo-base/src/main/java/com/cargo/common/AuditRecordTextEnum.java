package com.cargo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 企业证件审核枚举
 */
@Getter
@AllArgsConstructor
public enum AuditRecordTextEnum {
    //1.营业执照；2.企业授权书；3.身份证正面；4.身份证反面；5.企业道路运输经营许可证；6.危险化学品经营许可证；7.安全生产许可证
    AUDIT_RECORD_TEXT_ONE(1,"营业执照"),
    AUDIT_RECORD_TEXT_TWO(2,"企业授权书"),
    AUDIT_RECORD_TEXT_THREE(3,"身份证正面"),
    AUDIT_RECORD_TEXT_FOUR(4,"身份证反面"),
    AUDIT_RECORD_TEXT_FIVE(5,"企业道路运输经营许可证"),
    AUDIT_RECORD_TEXT_SIX(6,"危险化学品经营许可证"),
    AUDIT_RECORD_TEXT_SEVEN(7,"安全生产许可证"),
    ;


    public static AuditRecordTextEnum getEnum(Integer integer){
        for (AuditRecordTextEnum auditEnum:AuditRecordTextEnum.values()) {
            if(auditEnum.getType().equals(integer)){
                return auditEnum;
            }
        }
        return null;
    }


    private Integer type;
    private String message;
}
