package com.cargo.area.dto;

import com.cargo.area.entity.City;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class CityDto {


    @ApiModelProperty("大写字母")
    private String  firstEnglish;
    @ApiModelProperty("大写字母对应的城市")
    private List<City> cityList;
}
