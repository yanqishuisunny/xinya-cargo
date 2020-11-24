package com.cargo.bill.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xinzs
 * @Date: 2020/11/23 19:11
 */
@ApiModel("账目明细-账单列表")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BillOrderAccountDetailVo extends BillOrderVo implements Serializable {

    private String userName;
}
