package com.cargo.evaluates.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


import java.io.Serializable;

import java.lang.Long;
import java.time.LocalDateTime;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhl
 * @since 2020-11-09 14:29:33
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ExceptDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 创建人id
    */
    @ApiModelProperty("创建人id")
    private String createUserId;


    /**
    * 创建人姓名
    */
    @ApiModelProperty("创建人姓名")
    private String createUserName;


    /**
    * 创建人手机号
    */
    @ApiModelProperty("创建人手机号")
    private String createUserPhone;


    /**
    * 异常id
    */
    @ApiModelProperty("异常id")
    private Long exceptId;


    /**
    * 异常编号
    */
    @ApiModelProperty("异常编号")
    private String exceptNo;


    /**
    * 事故地点
    */
    @ApiModelProperty("事故地点")
    private String exceptPlace;


    /**
    * 异常状态  （暂定 0：新建，1：处理中，2：关闭 ）
    */
    @ApiModelProperty("异常状态  （暂定 0：新建，1：处理中，2：关闭 ）")
    private Integer exceptStatus;


    /**
    * 上报内容
    */
    @ApiModelProperty("上报内容")
    private String exceptTxt;


    /**
    * 异常类型  0:货损，1:交通事故，2:装卸异常，3:其他
    */
    @ApiModelProperty("异常类型  0:货损，1:交通事故，2:装卸异常，3:其他")
    private Integer exceptType;


    /**
    *
    */
    @ApiModelProperty("")
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtCreateState;
    private LocalDateTime gmtCreateEnd;


    /**
    *
    */
    @ApiModelProperty("")
    private LocalDateTime gmtModified;


    /**
    * 处理结果
    */
    @ApiModelProperty("处理结果")
    private String handleResult;


    /**
    * 处理时间
    */
    @ApiModelProperty("处理时间")
    private LocalDateTime handleTime;


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
    * 删除标记 0:已删除 1:未删除
    */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;


    /**
    * 经纬度
    */
    @ApiModelProperty("经纬度")
    private String lonLat;


    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String memo;


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


    /**
    * 处理结果
    */
    @ApiModelProperty("处理结果")
    private String processResult;


    /**
    * 关联的单据id（可能是作业单、运单、订单）
    */
    @ApiModelProperty("关联的单据id（可能是作业单、运单、订单）")
    private String relatId;


    /**
    * 关联的单据编号（可能是作业单号、运单号、订单号）
    */
    @ApiModelProperty("关联的单据编号（可能是作业单号、运单号、订单号）")
    private String relatNo;




    @ApiModelProperty("异常照片")
    private List<FileDto> files;

    @ApiModelProperty("需要删除异常照片的id")
    private List<String> fileId;



    public interface Create {
    }

    public interface Update {
    }
}
