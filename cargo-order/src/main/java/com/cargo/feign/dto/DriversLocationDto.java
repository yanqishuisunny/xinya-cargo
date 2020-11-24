package com.cargo.feign.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@Data
@ApiModel
@ToString
@EqualsAndHashCode(callSuper = false)
public class DriversLocationDto implements Serializable {


    private List<LocationDto> list;

    private String generalOrderId;

    private List<Integer> listWaybillStatus;

}
