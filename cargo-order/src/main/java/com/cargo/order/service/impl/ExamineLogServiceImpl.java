package com.cargo.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.order.entity.ExamineLogEntity;
import com.cargo.order.mapper.ExamineLogMapper;
import com.cargo.order.service.ExamineLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础数据审核日志表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-06
 */
@Service
public class ExamineLogServiceImpl extends ServiceImpl<ExamineLogMapper, ExamineLogEntity> implements ExamineLogService {

}
