package com.cargo.car.vo;

import com.cargo.car.entity.LineEntity;
import lombok.Data;

@Data
public class LineVo extends LineEntity {
    /**
     * 发货 省市县 6位编码
     * */
    private String senderAdCode;

    /**
     * 收货 省市县 6位编码
     * */
    private String deliveryAdCode;
}
