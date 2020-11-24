package com.cargo.order.common;

import com.cargo.order.entity.IntentionOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum IntentionOrderEnum {



    CONSIGNOR_ENUM_ZERO("00","意向"),
    CONSIGNOR_ENUM_ONE("01","待确定"),
    CONSIGNOR_ENUM_TWO("02","被拒绝"),
    CONSIGNOR_ENUM_TEN("10","待合作"),
    CONSIGNOR_ENUM_ELEVEN("11","已合作"),
    CONSIGNOR_ENUM_TWELVE("12","被拒绝"),
    CONSIGNOR_ENUM_TWENTY("20","拒绝"),
    CONSIGNOR_ENUM_TWENTYONE("21","拒绝"),


    CARRIER_ENUM_ZERO("00","意向"),
    CARRIER_ENUM_ONE("01","待合作"),
    CARRIER_ENUM_TWO("02","拒绝"),
    CARRIER_ENUM_TEN("10","待确定"),
    CARRIER_ENUM_ELEVEN("11","已合作"),
    CARRIER_ENUM_TWELVE("12","拒绝"),
    CARRIER_ENUM_TWENTY("20","被拒绝"),
    CARRIER_ENUM_TWENTYONE("21","被拒绝")
    ;


    public static IntentionOrderEnum getEnum(String status,Integer type){
        for (IntentionOrderEnum auditEnum: IntentionOrderEnum.values()) {
            if(type.equals(IntentionOrderEntity.TYPE_1)){
                if(auditEnum.name().contains("CONSIGNOR_ENUM") && auditEnum.getStatus().equals(status)){
                    return auditEnum;
                }
            }else if(type.equals(IntentionOrderEntity.TYPE_2)){
                if(auditEnum.name().contains("CARRIER_ENUM") && auditEnum.getStatus().equals(status)){
                    return auditEnum;
                }
            }
        }
        return null;
    }


    private String status;
    private String explain;
}
