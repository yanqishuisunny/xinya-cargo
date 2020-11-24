package com.cargo.location.vo;

import com.cargo.location.model.MapLocation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TrailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行驶轨迹
     */
    @ApiModelProperty("行驶轨迹(key:司机id,value:轨迹)")
    private Map<String,List<MapLocation>> map;


}
