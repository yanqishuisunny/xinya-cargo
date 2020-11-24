package com.cargo.location.vo;

import com.cargo.location.entity.SupplierInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 车辆表
 * </p>
 *
 * @author Carlos
 * @since 2019-08-06
 */
@Getter
@Setter
@ToString
public class SupplierVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 供应商列表
     */
    @ApiModelProperty("供应商列表")
    private List<SupplierInfoEntity> list;

    /**
     * 总数量
     */
    @ApiModelProperty("总数量")
    private int totalNum;


}
