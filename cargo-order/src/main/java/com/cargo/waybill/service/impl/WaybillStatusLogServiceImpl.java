package com.cargo.waybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.waybill.entity.WaybillStatusLogEntity;
import com.cargo.waybill.mapper.WaybillStatusLogMapper;
import com.cargo.waybill.service.WaybillStatusLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运单跟踪表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Service
public class WaybillStatusLogServiceImpl extends ServiceImpl<WaybillStatusLogMapper, WaybillStatusLogEntity> implements WaybillStatusLogService {

}
