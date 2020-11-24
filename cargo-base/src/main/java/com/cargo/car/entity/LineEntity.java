package com.cargo.car.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("line")
public class LineEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "line_id", type = IdType.UUID)
    private String lineId;

    /**
     * 发货-区域ID 省ID
     */
    private String senderAreaProvinceId;

    private String senderAreaProvinceName;

    /**
     * 发货-区域ID 市ID
     */
    private String senderAreaCityId;

    private String senderAreaCityName;

    /**
     * 发货-区域ID 镇ID
     */
    private String senderAreaTownId;

    private String senderAreaTownName;

    private String deliveryAreaProvinceId;

    private String deliveryAreaProvinceName;

    private String deliveryAreaCityId;

    private String deliveryAreaCityName;

    private String deliveryAreaTownId;

    private String deliveryAreaTownName;



    private String createUser;

    private String updateUser;



}
