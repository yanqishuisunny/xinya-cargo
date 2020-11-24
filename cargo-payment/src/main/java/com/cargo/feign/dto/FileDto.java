package com.cargo.feign.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class FileDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long fileId;

    /**
     * 关联的表id
     */

    @ApiModelProperty("关联的表id")
    private String relatId;

    /**
     * 文件类型，0:异常上报;1装车照片；2 装车过磅单， 3协车照片；4 卸车过磅单
     */

    @ApiModelProperty("文件类型，0:异常上报;1装车照片；2 装车过磅单， 3协车照片；4 卸车过磅单")
    private Integer fileType;

    /**
     * 文件的URL地址
     */
    @ApiModelProperty("文件的URL地址")
    private String fileUrl;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;



}
