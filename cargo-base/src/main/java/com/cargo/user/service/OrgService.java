package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.vo.OrgVo;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
public interface OrgService extends IService<OrgEntity> {

    Page<OrgVo> queryForExamineList(OrgDto dto, Page<OrgVo> page);

    boolean editStatus(OrgDto dto);
}
