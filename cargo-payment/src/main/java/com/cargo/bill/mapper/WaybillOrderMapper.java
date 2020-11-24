package com.cargo.bill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.bill.entity.WaybillOrderEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 帐单扩展_运单(司机对账)表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-11-18
 */
@Repository
public interface WaybillOrderMapper extends BaseMapper<WaybillOrderEntity> {

}
