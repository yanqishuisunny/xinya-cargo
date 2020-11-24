package com.cargo.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cargo.order.entity.GoodsCategoryEntity;
import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class GoodsCategoryVo extends TreeNode {

    /**
     * 货物种类ID
     */
    @TableId(value = "goods_category_id", type = IdType.UUID)
    private String goodsCategoryId;

    /**
     * 货物分类名称
     */
    private String goodsCategoryName;
    /**
     * 父级ID 0：顶级
     * */
    private String parentId;


    private String createUser;

    private String updateUser;

}
