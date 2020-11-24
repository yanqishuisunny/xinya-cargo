package com.cargo.feign.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("carrier_release")
public class CarrierReleaseEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String carrierReleaseId;

    /**
     * 承运商信息发布状态 状态( 0：创建  1:待审核（已提交）   2：审核通过   3：过期失效，-2 审核不过，-1：撤销)
     */
    private String status;

    /**
     * 有效天数
     */
    private Integer expiryDays;

    /**
     * 发布日期
     */
    private String expiryDaysDate;

    /**
     * 有效期日期(发布日期加上有效天数算出)
     */
    private String validityDate;

    /**
     * 时效天数
     */
    private Integer prescription;

    private String lineSenderAreaProvinceId;

    private String lineSenderAreaProvinceName;

    private String lineSenderAreaCityId;

    private String lineSenderAreaCityName;

    private String lineSenderAreaTownId;

    private String lineSenderAreaTownName;

    private String lineDeliveryAreaProvinceId;

    private String lineDeliveryAreaProvinceName;

    private String lineDeliveryAreaCityId;

    private String lineDeliveryAreaCityName;

    private String lineDeliveryAreaTownId;

    private String lineDeliveryAreaTownName;

    private String carrierStartTime;

    private String carrierEndTime;

    private BigDecimal linePrice;
    /**
     * 计费方式
     */
    private String priceTypeId;
    /**
     * 备注
     */
    private String carrierRemark;

    private String createUser;

    private String updateUser;



}
