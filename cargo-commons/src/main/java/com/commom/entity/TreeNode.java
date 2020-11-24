package com.commom.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形实体父类
 */


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreeNode {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "父级ID")
    private String parentId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "子集")
    private List<TreeNode> childrens = new ArrayList<>();

}
