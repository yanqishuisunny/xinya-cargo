package com.cargo.area.vo;

import com.cargo.area.dto.ProvinceDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@ApiModel("省列表 带字母")
@Data
public class ProvinceVo {

    @ApiModelProperty("字母列表")
    private Set<String> english;
    @ApiModelProperty("省列表")
    private List<ProvinceDto> provinceDtoList;
}
