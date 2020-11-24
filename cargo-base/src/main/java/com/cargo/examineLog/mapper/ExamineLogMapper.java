package com.cargo.examineLog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.examineLog.entity.ExamineLogEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 基础数据审核日志表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-11-06
 */
@Repository
public interface ExamineLogMapper extends BaseMapper<ExamineLogEntity> {

}
