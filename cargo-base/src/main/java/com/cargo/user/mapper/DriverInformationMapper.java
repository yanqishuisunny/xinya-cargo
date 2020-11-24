package com.cargo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.vo.DriverInCountVo;
import com.cargo.user.vo.DriverInformationVo;
import com.cargo.user.vo.DriverMessageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 司机信息表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Repository
public interface DriverInformationMapper extends BaseMapper<DriverInformationEntity> {
    /**
     * 将公司的司机信息放入redis
     * @param orgId
     * @return
     */
    List<DriverMessageVo> driverToRedis(@Param("orgId") String orgId);

    List<DriverInformationVo> queryForExamineList(@Param("dto") DriverInformationDto dto, Page<DriverInformationVo> page);

    /**
     * 一段时间内的注册数量
     * @param start
     * @param end
     * @return
     */
    List<DriverInCountVo> hours(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<DriverInCountVo> days(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<DriverInCountVo> months(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
