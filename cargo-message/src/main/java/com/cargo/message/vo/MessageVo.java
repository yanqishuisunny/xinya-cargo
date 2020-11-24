package com.cargo.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;


/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16 17:16:16
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公司ID")
    private String orgId;
        /**
     * 消息确认时间
     */
    @ApiModelProperty("消息确认时间")
    private Long ackTime;
        /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    private String content;
        /**
     * 
     */
    @ApiModelProperty("")
    private String createUser;
        /**
     * 
     */
    @ApiModelProperty("")
    private Date gmtCreate;
        /**
     * 
     */
    @ApiModelProperty("")
    private Date gmtModified;
        /**
     * 
     */
    @ApiModelProperty("")
    private String isAble;
        /**
     * 已读
     */
    @ApiModelProperty("已读")
    private String isRead;
        /**
     * 
     */
    @ApiModelProperty("")
    private String messageId;
        /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String refuseRemark;
        /**
     * 消息来源
     */
    @ApiModelProperty("消息来源")
    private Integer source;
        /**
     * 消息类型
     */
    @ApiModelProperty("消息类型")
    private Integer type;
        /**
     * 
     */
    @ApiModelProperty("")
    private String updateUser;
        /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;
    

}