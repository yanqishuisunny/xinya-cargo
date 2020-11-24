package com.cargo.evaluates.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptVo implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 异常id
     */
    @TableId(value = "except_id", type = IdType.UUID)
    private String exceptId;

    /**
     * 异常编号
     */
    @ApiModelProperty("异常编号")
    private String exceptNo;

    /**
     * 关联的单据id（可能是作业单、运单、订单）
     */
    @ApiModelProperty("可能是作业单、运单、订单")
    private String relatId;

    /**
     * 关联的单据编号（可能是作业单号、运单号、订单号）
     */
    @ApiModelProperty("关联的单据编号（可能是作业单号、运单号、订单号）")
    private String relatNo;

    /**
     * 异常类型  0:货损，1:交通事故，2:装卸异常，3:其他
     */
    @ApiModelProperty("异常类型  0:货损，1:交通事故，2:装卸异常，3:其他")
    private Integer exceptType;

    /**
     * 事故地点
     */
    @ApiModelProperty("事故地点")
    private String exceptPlace;

    /**
     * 经纬度
     */
    @ApiModelProperty("经纬度")
    private String lonLat;

    /**
     * 异常状态  （暂定 0：新建，1：处理中，2：关闭 ）
     */
    @ApiModelProperty("异常状态  （暂定 0：新建，1：处理中，2：关闭 ）")
    private Integer exceptStatus;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    /**
     * 处理结果
     */
    @ApiModelProperty("处理结果")
    private String processResult;

    /**
     * 处理人id
     */
    @ApiModelProperty("处理人id")
    private String handleUserId;

    /**
     * 处理人姓名
     */
    @ApiModelProperty("处理人姓名")
    private String handleUserName;

    /**
     * 处理结果
     */
    @ApiModelProperty("处理结果")
    private String handleResult;


    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private String createUserId;


    @ApiModelProperty("异常照片")
    private List<FileVo> files;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private String gmtModified;

    /**
     * 是否有效
     */
    @ApiModelProperty("是否有效")
    private Integer isAble;

    /**
     * 上报内容
     */
    @ApiModelProperty("上报内容")
    private String exceptTxt;

    /**
     * 处理时间
     */
    @ApiModelProperty("处理时间")
    private String handleTime;

    /**
     * 创建人手机号
     */
    @ApiModelProperty("创建人手机号")
    private String createUserPhone;
    /**
     * 创建人姓名
     */
    @ApiModelProperty("创建人姓名")
    private String createUserName;
    /**
     * 创建人公司id
     */
    @ApiModelProperty("创建人公司id")
    private String orgId;
    /**
     * 创建人公司
     */
    @ApiModelProperty("创建人公司")
    private String orgName;


}
