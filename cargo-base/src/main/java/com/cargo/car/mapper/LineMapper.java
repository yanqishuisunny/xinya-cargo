package com.cargo.car.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.car.entity.LineEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.car.vo.LineVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Repository
public interface LineMapper extends BaseMapper<LineEntity> {

    List<LineVo> queryForList(@Param("userId") String userId, Page<LineVo> page);

    LineVo queryForOne(@Param("id")String id);
}
