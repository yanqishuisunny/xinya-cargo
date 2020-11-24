package com.cargo.area.vo;

import com.cargo.area.dto.CityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@ApiModel("城市列表带字母")
public class CityVo {
    @ApiModelProperty("城市字母列表")
    private Set<String> english;
    @ApiModelProperty("城市列表")
    private List<CityDto> cityList;
}
