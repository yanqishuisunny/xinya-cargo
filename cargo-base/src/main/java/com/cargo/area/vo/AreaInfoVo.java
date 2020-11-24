package com.cargo.area.vo;

import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 行政区域
 * @author 何立辉
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AreaInfoVo extends TreeNode {

    /**
     *  统计区划代码
     */
    @ApiModelProperty("统计区划代码")
    private String code;

    /**
     *  GBT2260区划代码
     */
    @ApiModelProperty("GBT2260区划代码")
    private String gbtCode;
    /**
     * 省名称
     */
    @ApiModelProperty("省名称")
    private String provinceName;

    /**
     * 省id
     */
    @ApiModelProperty("省id")
    private String provinceId;

    /**
     * 城市名称
     */
    @ApiModelProperty("城市名称")
    private String cityName;

    /**
     * 城市id
     */
    @ApiModelProperty("城市id")
    private String cityId;

    /**
     * 区县名称
     */
    @ApiModelProperty("区县名称")
    private String countyName;

    /**
     * 区县ID
     */
    @ApiModelProperty("区县ID")
    private String countyId;

    /**
     * 等级
     */
    @ApiModelProperty("等级")
    private Integer level;

    private List<AreaInfoVo> child;

}
