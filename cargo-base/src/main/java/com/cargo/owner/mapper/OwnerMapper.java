package com.cargo.owner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.owner.dto.OwnerDto;
import com.cargo.owner.entity.OwnerEntity;
import com.cargo.owner.vo.OwnerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 组织扩展表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Repository
public interface OwnerMapper extends BaseMapper<OwnerEntity> {

    List<OwnerVo> queryForExamineList(@Param("dto") OwnerDto dto, Page<OwnerVo> page);
}
