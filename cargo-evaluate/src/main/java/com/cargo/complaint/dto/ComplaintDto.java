package com.cargo.complaint.dto;


import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zhl
 * @since 2020-11-09 09:43:27
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ComplaintDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String complaintId;


    /**
    * 状态（0，撤销；1新建，2处理 ，3结束 ）
    */
    @ApiModelProperty("状态（0，撤销；1新建，2处理 ，3结束 ）")
    private Integer complaintStatus;


    /**
    * 投诉文本
    */
    @ApiModelProperty("投诉文本")
    private String complaintTxt;


    /**
    * 处理人
    */
    @ApiModelProperty("处理人")
    private String conductor;


    /**
    * 处理人id
    */
    @ApiModelProperty("处理人id")
    private String conductorId;


    /**
    * 创建时间
    */
    @ApiModelProperty("处理时间")
    private LocalDateTime conductorTime;

    @ApiModelProperty("处理结果")
    private String conductorTxt;


    /**
    * 投诉id
    */
    @ApiModelProperty("投诉id")
    private String createId;


    /**
    * 投诉人
    */
    @ApiModelProperty("投诉人")
    private String createName;


    /**
    * 投诉人
    */
    @ApiModelProperty("投诉人")
    private String createPhone;


    /**
    * 单号id
    */
    @ApiModelProperty("单号id")
    private String documentId;


    /**
    * 投诉单号
    */
    @ApiModelProperty("投诉单号")
    private String documentNumber;


    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtCreateStart;
    private LocalDateTime gmtCreateEnd;


    /**
    * 修改时间
    */
    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;


    /**
    * 删除标记 0:已删除 1:未删除
    */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;


    /**
    *
    */
    @ApiModelProperty("")
    private String orgId;


    /**
    *
    */
    @ApiModelProperty("")
    private String orgName;


    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String remark;


    public interface Create {
    }

    public interface Update {
    }
}
