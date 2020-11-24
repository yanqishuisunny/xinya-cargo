package com.cargo.location.service;

import com.cargo.location.dto.DriversLocationDto;
import com.cargo.location.dto.LocationDto;
import com.cargo.location.model.MapLocation;
import com.cargo.location.vo.TrailVo;

import java.util.List;

/**
 * <p>Title： LocationService </p>
 * <p>Description： </p>
 * <p>Company：ail </p>
 *
 * @author Carlos
 * @version V1.0
 * @date 2020/1/13 15:50
 */
public interface LocationService {


    /**
     * 将三方数据写入mongo
     *
     * @param result 定位数据
     * @return 定位数据
     */
    boolean addMongoData(List<MapLocation> result, String userId);



    /**
     * 查询车辆轨迹和总里程数
     *
     * @param dto
     * @return 定位数据
     */
    TrailVo queryLocationList(DriversLocationDto dto);


}
