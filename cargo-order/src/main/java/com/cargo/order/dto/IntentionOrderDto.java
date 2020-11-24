package com.cargo.order.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class IntentionOrderDto {
    private List<GoodsDto> listGoods;
    /**
     * 当前登录人ID
     * */
    private String currentUserId;

    @ApiModelProperty("意向单号")
    private String intentionOrderNo;

    @ApiModelProperty("查询类型 1：我发出  2：我收到")
    private String queryWay;

    @ApiModelProperty("货主发货 省市县 6位编码")
    private String senderAdCode;

    @ApiModelProperty("货主收货 省市县 6位编码")
    private String deliveryAdCode;

    @TableId(value = "intention_order_id", type = IdType.UUID)
    private String intentionOrderId;

    @ApiModelProperty("意向单状态")
    private String intentionStatus;

    @ApiModelProperty("1:意向发起人是货主    2：意向发起人是承运商")
    private Integer type;

    @ApiModelProperty("意向单发向")
    private String toUserId;

    @ApiModelProperty("type为1时:承运商发布信息ID    2时：货主发布信息ID")
    private String resourceId;

    @ApiModelProperty("货主ID 等同于 创建人ID")
    private String cargoUserId;

    @ApiModelProperty("发货单位")
    private String senderOrgName;

    @ApiModelProperty("发货人")
    private String senderUserName;

    @ApiModelProperty("发货人联系方式")
    private String senderUserPhone;

    @ApiModelProperty("装货详细地址")
    private String senderAreaDetail;

    @ApiModelProperty("装货开始时间")
    private String senderStartTime;

    @ApiModelProperty("装货结束时间")
    private String senderEndTime;

    @ApiModelProperty("收货详细地址")
    private String deliveryAreaDetail;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("计费方式ID")
    private String priceTypeId;

    @ApiModelProperty("运输类型ID")
    private String trspotTypeId;

    @ApiModelProperty("车辆要求ID")
    private String carTypeId;

    @ApiModelProperty("车辆尺寸ID")
    private String carSizeId;

    @ApiModelProperty("审核备注")
    private String checkRemark;

    @ApiModelProperty("特殊要求")
    private String consignorRemark;

    @ApiModelProperty("收货单位")
    private String deliveryOrgName;

    @ApiModelProperty("收货人")
    private String deliveryUserName;

    @ApiModelProperty("收货人联系方式")
    private String deliveryUserPhone;

    @ApiModelProperty("拒绝备注")
    private String refuseRemark;


    private String createUser;

    private String upStringUser;
}
