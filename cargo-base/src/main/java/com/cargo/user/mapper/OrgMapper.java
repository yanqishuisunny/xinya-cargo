package com.cargo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.vo.OrgVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 组织表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Repository
public interface OrgMapper extends BaseMapper<OrgEntity> {

    List<OrgVo> queryForExamineList(@Param("dto") OrgDto dto, Page<OrgVo> page);
}
