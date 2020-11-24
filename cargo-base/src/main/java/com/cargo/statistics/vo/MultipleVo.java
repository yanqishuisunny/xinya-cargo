package com.cargo.statistics.vo;

import io.swagger.annotations.ApiModel;
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
 * @since 2020-11-10 18:07:57
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MultipleVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 增加的数量
     */
    @ApiModelProperty("增加的数量")
    private Integer count;
        /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;
        /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date gmtModified;
        /**
     *
     */
    @ApiModelProperty("")
    private String id;
        /**
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
        /**
     * 是否启用（0否，1 是）
     */
    @ApiModelProperty("是否启用（0否，1 是）")
    private Integer isStart;
        /**
     * 放大倍数
     */
    @ApiModelProperty("放大倍数")
    private Integer multiple;
        /**
     * 1.车辆，2.司机
     */
    @ApiModelProperty("1.车辆，2.司机")
    private Integer type;


}
