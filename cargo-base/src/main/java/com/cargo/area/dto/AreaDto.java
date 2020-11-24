package com.cargo.area.dto;


import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * <p>
 * 行政区域
 * </p>
 *
 * @author helihui
 * @since 2019-07-25 10:32:35
 */


@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AreaDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = AreaDto.Update.class,message = "区域ID不能为空")
    @ApiModelProperty("区域ID" )
    private String id;

    @NotNull(groups = {AreaDto.Create.class, AreaDto.Create.class}, message = "父菜单不能为空")
    @ApiModelProperty("父菜单ID，一级菜单为0")
    private String parentId;

    @NotNull(groups = {AreaDto.Create.class, AreaDto.Create.class}, message = "名称不能为空")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 名称缩写
     */
    @ApiModelProperty("名称缩写")
    private String nameAbbreviation;

    @NotNull(groups = {AreaDto.Create.class, AreaDto.Create.class}, message = "编号不能为空")
    @ApiModelProperty("编号")
    private String code;

    @NotNull(groups = {AreaDto.Create.class, AreaDto.Create.class}, message = "GBT编码不能为空")
    @ApiModelProperty("GBT编码")
    private String gbtCode;

    @NotNull(groups = {AreaDto.Create.class, AreaDto.Create.class}, message = "等级不能为空")
    @ApiModelProperty("等级，默认从4级开始")
    private Integer level;

    @ApiModelProperty("显示序号")
    private Integer displayOrder;


    @ApiModelProperty("交通局区域代码")
    private String rtaId;

    public interface Create {}

    public interface Update {}

}