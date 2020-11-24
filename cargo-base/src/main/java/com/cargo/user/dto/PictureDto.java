package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29 11:10:25
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PictureDto extends Convert implements Serializable {

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

    
    public interface Create {
    }

    public interface Update {
    }
}