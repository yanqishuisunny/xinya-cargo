package com.cargo.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
 * @since 2020-10-29 11:10:25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PictureVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 图片类型
     */
    @ApiModelProperty("图片类型")
    private Integer fileType;
        /**
     * 文件的URL地址
     */
    @ApiModelProperty("文件的URL地址")
    private String fileUrl;
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
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
        /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
        /**
     * 1:货主 2:司机 3:承运商
     */
    @ApiModelProperty("1:货主 2:司机 3:承运商")
    private Integer orgType;
        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String pictureId;
        /**
     * 关联id
     */
    @ApiModelProperty("关联id")
    private String relationId;
        /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;
    

}