package com.cargo.menedata.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageOcrDto extends Convert {

    @ApiModelProperty("0表示身份证识别，1营业执照识别，2表示驾驶证识别，3行驶证识别,8-安全生产许可证")
    private String type;

    @ApiModelProperty("图片的路径")
    private String imagePath;

    @ApiModelProperty("身份证的边，“front”表示正面，“back”表示反面（身份证和行驶证识别会用到这个参数）")
    private  String idCardSide;


    @ApiModelProperty("车牌号")
    private String carNo;
    @ApiModelProperty("牌照类型(目前让前端写死为黄色)")
    private String licenceType;
}
