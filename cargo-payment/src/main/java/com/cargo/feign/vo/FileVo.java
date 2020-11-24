package com.cargo.feign.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileVo implements Serializable {

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

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

    /**
     * 是否有效
     */

    private Integer isAble;

}
