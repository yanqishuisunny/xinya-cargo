package com.cargo.area.dto;

import com.cargo.area.entity.City;
import com.cargo.area.entity.Province;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class ProvinceDto {

    @ApiModelProperty("省")
    private Province province;
    @ApiModelProperty("城市列表")
    private List<City> cityList;
}
