package com.cargo.order.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author jobob
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goods_category")
public class GoodsCategoryEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PARENTID_0 = "0";

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

    private Integer level;


    private String createUser;

    private String updateUser;




}
