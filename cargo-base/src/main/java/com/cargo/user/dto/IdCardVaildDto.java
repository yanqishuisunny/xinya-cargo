package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 身份认证
 * </p>
 *
 * @author 何立辉
 * @since 2019-08-31 19:07:56
 */

@ApiModel
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class IdCardVaildDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("所属组织ID")
    private String orgId;

    @ApiModelProperty("活体检测图")
    @NotNull(message = "活体检测图不能为空")
    private String livingImgUrl;

    @ApiModelProperty("身份证发证机关")
    @NotNull(message = "身份证发证机关不能为空")
    private String idCardAuthority;

    @ApiModelProperty("身份证名称")
    @NotNull(message = "身份证名称不能为空")
    private String idcardName;

    @ApiModelProperty("身份证号")
    @NotNull(message = "身份证号不能为空")
    private String idcardNo;

    @ApiModelProperty("身份证反面照片")
    private String idcardReUrl;

    
    @ApiModelProperty("身份证正面照片")
    private String idcardUrl;

    
    @ApiModelProperty("身份证发证结束时间")
    private String idCardEndTime;

    
    @ApiModelProperty("身份证发证开始时间")
    private String idCardStartTime;


}