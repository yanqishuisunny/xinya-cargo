package com.cargo.account.dto;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Auther: xinzs
 * @Date: 2020/11/10 15:35
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AccountOrderListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 批次号
     */
    private String accountNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 合同日期
     */
    private String gmtCreate;

    private String startTime;
    private String endTime;

    /**
     * 操作人
     */
    private String createUser;

    /**
     * 账目状态（1、已锁；2、未锁；）
     */
    private String accountStatus;

    private String orgId;

}
