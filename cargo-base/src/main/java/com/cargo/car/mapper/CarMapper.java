package com.cargo.car.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.car.dto.CarDto;
import com.cargo.car.entity.CarEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.car.vo.CarInCountVo;
import com.cargo.car.vo.CarMessageVo;
import com.cargo.car.vo.CarVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
public interface CarMapper extends BaseMapper<CarEntity> {


    List<CarVo> queryForList(@Param("dto") CarDto dto, Page<CarVo> page);

    /**
     * 查找公司下车辆信息然后将信息放入redis中
     * @param orgId
     * @return
     */
    List<CarMessageVo> carMessageToRedis(@Param("orgId") String orgId);

    /**
     * 查询一段车辆的数量
     * @param start
     * @param end
     * @return
     */
    List<CarInCountVo> hours(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    List<CarInCountVo> days(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    List<CarInCountVo> months(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
