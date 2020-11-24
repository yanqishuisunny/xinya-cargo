package com.cargo.evaluates.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.evaluates.entity.Evaluate;
import com.cargo.evaluates.mapper.EvaluateMapper;
import com.cargo.evaluates.service.EvaluateService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论主表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {

}
