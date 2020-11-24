package com.cargo.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods_type")
public class GoodsTypeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "goods_type_id", type = IdType.UUID)
    private String goodsTypeId;

    /**
     * 货物类型名称
     */
    private String goodsTypeName;



    private String createUser;

    private String updateUser;



}
