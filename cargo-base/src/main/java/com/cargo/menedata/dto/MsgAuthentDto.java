package com.cargo.menedata.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 信息认证的参数类
 * @author Alex
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MsgAuthentDto extends Convert {

    @ApiModelProperty("0身份信息认证，1企业信息认证，2企业法人股东高管核查,3驾驶证核查")
    private String type;

    @ApiModelProperty("姓名")
    private String  userName;
    @ApiModelProperty("身份证号")
    private String  idCardNo;


    @ApiModelProperty("社会信用代码")
    private String orgCode;
    @ApiModelProperty("企业名称")
    private String corpName;
    @ApiModelProperty("被查询企业工商注册号")
    private String registerNo;


    /**
     * 企业法人股东高管核查(userName idCardNo corpName)
     * 驾驶证核查(userName idCardNo)
     */
    @ApiModelProperty("企业法人股东高管核查")
    private String corpId;












}
