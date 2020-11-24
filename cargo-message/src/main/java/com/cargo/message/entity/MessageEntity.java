package com.cargo.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("message")
public class MessageEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	private String orgId;
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String messageId;
    /**
     * 用户ID
     */
	private String userId;
    /**
     * 消息类型
     */
	private Integer type;
    /**
     * 消息内容
     */
	private String content;
    /**
     * 已读
     */
	private Boolean isRead;
    /**
     * 消息确认时间
     */
	private Long ackTime;
    /**
     * 消息来源
     */
	private Integer source;
    /**
     * 备注
     */
	private String refuseRemark;
	private String createUser;
	private String updateUser;

}
