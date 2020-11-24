package com.cargo.complaint.service.impl;

;import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.complaint.entity.ComplaintLogEntity;
import com.cargo.complaint.mapper.ComplaintLogMapper;
import com.cargo.complaint.service.ComplaintLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 投诉log表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-06
 */
@Service
public class ComplaintLogServiceImpl extends ServiceImpl<ComplaintLogMapper, ComplaintLogEntity> implements ComplaintLogService {

}
