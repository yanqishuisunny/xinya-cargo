package com.cargo.evaluates.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.evaluates.entity.ExceptEntity;
import com.cargo.evaluates.mapper.ExceptMapper;
import com.cargo.evaluates.service.ExceptService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 异常单据表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Service
public class ExceptServiceImpl extends ServiceImpl<ExceptMapper, ExceptEntity> implements ExceptService {

}
