package com.cargo.order.service.impl;

import com.cargo.order.entity.IntentionLogEntity;
import com.cargo.order.mapper.IntentionLogMapper;
import com.cargo.order.service.IntentionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 意向单状态流转表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-06
 */
@Service
public class IntentionLogServiceImpl extends ServiceImpl<IntentionLogMapper, IntentionLogEntity> implements IntentionLogService {

}
