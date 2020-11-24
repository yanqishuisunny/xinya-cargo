package com.cargo.order.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 意向单状态流转表
 * </p>
 *
 * @author jobob
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("intention_log")
public class IntentionLogEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_10 = "10";
    public static final String TYPE_20 = "20";

    @TableId(value = "intention_log_id", type = IdType.UUID)
    private String intentionLogId;

    /**
     * 1:货主信息     2：承运商信息
     */
    private Integer intentionType;

    private String oldStatus;

    private String newStatus;

    /**
     * 描述
     */
    private String context;

    /**
     * 10:意向单    20：订单
     * */
    private String type;
    /**
     * 备注
     */
    private String remark;

    private String createUser;

    private String updateUser;


}
