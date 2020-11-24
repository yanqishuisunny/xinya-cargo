package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 图片表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_picture")
public class PictureEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "picture_id")
    private String pictureId;
    /**
     * 用户id
     */
	private String userId;
    /**
     * 关联id
     */
	private String relationId;
    /**
     * 1:货主 2:司机 3:承运商
     */
	private Integer orgType;
    /**
     * 图片类型
     */
	private Integer fileType;
    /**
     * 文件的URL地址
     */
	private String fileUrl;
    /**
     * 备注
     */
	private String memo;


}
