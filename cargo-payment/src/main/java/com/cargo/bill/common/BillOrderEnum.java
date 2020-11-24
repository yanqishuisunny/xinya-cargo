package com.cargo.bill.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Auther: xinzs
 * @Date: 2020/11/9 16:59
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BillOrderEnum {

    STATUS_TO_BE_REVIEWED(1,"待审核"),
    STATUS_TO_BE_PAID(2,"待付款"),
    STATUS_PAID(3,"已付款")







    ;


    public static BillOrderEnum getEnum(Integer integer){
        for (BillOrderEnum auditEnum:BillOrderEnum.values()) {
            if(auditEnum.getType().equals(integer)){
                return auditEnum;
            }
        }
        return null;
    }


    private Integer type;
    private String message;
}
