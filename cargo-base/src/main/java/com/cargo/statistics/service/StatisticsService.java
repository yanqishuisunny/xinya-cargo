package com.cargo.statistics.service;

import com.cargo.statistics.vo.CountsVo;

/**
 * 统计
 */
public interface StatisticsService {
    /**
     * 统计注册的数量
     * @param type
     * @return
     */
    CountsVo counts(String type);
}
