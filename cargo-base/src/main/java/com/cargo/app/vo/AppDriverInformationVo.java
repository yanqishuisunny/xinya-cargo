package com.cargo.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


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
public class AppDriverInformationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否实名认证 true有 false没有
     */
    @ApiModelProperty("是否实名认证 true有 false没有")
    private Boolean authenName;

    /**
     * 审核状态 0:待实名认证 1:待从业资格认证 2:待审核  3:审核拒绝  4:审核通过 5:没有提交证件信息
     */
    @ApiModelProperty("审核状态 0:待实名认证 1:待从业资格认证 2:待审核  3:审核拒绝  4:审核通过 5:没有提交证件信息")
    private Integer auditStatus;

    /**
     * 是否有车辆 true有 false没有
     */
    @ApiModelProperty("是否有车辆 true有 false没有")
    private Boolean carInfo;


}