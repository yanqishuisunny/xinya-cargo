package com.cargo.complaint.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * <p>
 *
 * </p>
 *
 * @author 开发者
 * @since 2020-11-06 14:42:06
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ComplaintLogDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 投诉id
    */
    @ApiModelProperty("投诉id")
    private String complaintId;


    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String complaintLogId;


    /**
    * 状态
    */
    @ApiModelProperty("状态")
    private Integer complaintStatus;


    /**
    * 投诉文本
    */
    @ApiModelProperty("投诉文本")
    private String complaintTxt;


    /**
    * 创建人
    */
    @ApiModelProperty("创建人")
    private String createId;


    /**
    * 创建人
    */
    @ApiModelProperty("创建人")
    private String createName;


    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;


    /**
    * 修改时间
    */
    @ApiModelProperty("修改时间")
    private Date gmtModified;


    /**
    * 删除标记 0:已删除 1:未删除
    */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;


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
