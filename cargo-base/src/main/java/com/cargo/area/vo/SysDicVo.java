package com.cargo.area.vo;

import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author 何立辉
 * @since 2019-09-04 11:11:38
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysDicVo extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @ApiModelProperty("字典编码")
    private String dicCode;
    
    @ApiModelProperty("字典名")
    private String dicName;
    
    @ApiModelProperty("字典值")
    private String dicValue;
    
    @ApiModelProperty("是否默认[0：否,1:是]")
    private Boolean flgDefault;
    
    @ApiModelProperty("状态[0：禁用,1:启用]")
    private Integer status;

    @ApiModelProperty("排序号")
    private Integer orderNum;

}