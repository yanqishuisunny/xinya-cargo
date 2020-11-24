package com.cargo.evaluates.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 图片存储表
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("c_file")
public class FileEntity  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "file_id", type = IdType.UUID)
    private Long fileId;

    /**
     * 关联的表id
     */
    private String relatId;

    /**
     * 文件类型，0:异常上报;1装车照片；2 装车过磅单， 3协车照片；4 卸车过磅单
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
