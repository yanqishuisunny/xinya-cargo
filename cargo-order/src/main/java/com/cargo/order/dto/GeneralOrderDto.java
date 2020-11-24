package com.cargo.order.dto;

import com.cargo.order.entity.GeneralOrderEntity;
import lombok.Data;

@Data
public class GeneralOrderDto extends GeneralOrderEntity {
    private String loginUserId;

    private String startTime;

    private String endTime;
}
