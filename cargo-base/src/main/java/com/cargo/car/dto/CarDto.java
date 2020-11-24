package com.cargo.car.dto;

import com.cargo.car.entity.CarEntity;
import lombok.Data;

@Data
public class CarDto extends CarEntity {

    //查询  1：所有   2：自己
     private String type;
}
