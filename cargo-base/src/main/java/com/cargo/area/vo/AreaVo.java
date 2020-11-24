package com.cargo.area.vo;

import com.cargo.area.entity.AreaEntity;
import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class AreaVo extends TreeNode {

    /**
     * 区域编码
     */
    @ApiModelProperty("区域编码")
    private String code;
    @ApiModelProperty("区域编码")
    private String gbtCode;

    @ApiModelProperty("交通局区域代码")
    private String rtaId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("名称缩写")
    private String nameAbbreviation;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;

    /**
     * 是否有效
     */
    @ApiModelProperty("是否有效")
    private Integer isAble;

    private List<AreaEntity> child;

}
