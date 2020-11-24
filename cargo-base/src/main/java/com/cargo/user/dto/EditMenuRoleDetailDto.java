package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10 11:07:07
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EditMenuRoleDetailDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 菜单id
     */
    @ApiModelProperty("角色id")
    private String roleMainId;
    
    /**
    * 菜单id
    */
    @ApiModelProperty("菜单id")
    private List<String> baseInfoIds;

    


    

}