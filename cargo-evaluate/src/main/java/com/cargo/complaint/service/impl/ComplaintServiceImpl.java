package com.cargo.complaint.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.complaint.entity.ComplaintEntity;
import com.cargo.complaint.mapper.ComplaintMapper;
import com.cargo.complaint.service.ComplaintService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 投诉表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-06
 */
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, ComplaintEntity> implements ComplaintService {

}
