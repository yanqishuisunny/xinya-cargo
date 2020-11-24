package com.cargo.point.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;
import java.lang.String;


/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16 17:17:47
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PointAccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公司ID")
    private String orgId;
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
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String pointAccountId;
        /**
     * 分值
     */
    @ApiModelProperty("分值")
    private String pointCount;
        /**
     * 当前积分
     */
    @ApiModelProperty("当前积分")
    private String pointCountNewest;
        /**
     * 积分来源
     */
    @ApiModelProperty("积分来源")
    private String pointSource;
        /**
     * 积分类型（1、收入；2、支出）
     */
    @ApiModelProperty("积分类型（1、收入；2、支出）")
    private String pointType;
        /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String refuseRemark;
        /**
     * 
     */
    @ApiModelProperty("")
    private String updateUser;
    

}