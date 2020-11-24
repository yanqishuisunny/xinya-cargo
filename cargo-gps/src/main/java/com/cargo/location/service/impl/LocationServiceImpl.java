package com.cargo.location.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cargo.feign.service.FeignService;
import com.cargo.location.dto.DriversLocationDto;
import com.cargo.location.dto.LocationDto;
import com.cargo.location.model.MapLocation;
import com.cargo.location.service.LocationService;
import com.cargo.location.vo.TrailVo;
import com.cargo.location.vo.UserInfoVo;
import com.commom.cache.CachePre;
import com.commom.exception.BussException;
import com.commom.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title： LocationServiceImpl </p>
 * <p>Description： </p>
 * <p>Company：ail </p>
 *
 * @author Carlos
 * @version V1.0
 * @date 2020/1/13 15:53
 */
@Slf4j
@Service
public class LocationServiceImpl implements LocationService {


    /**
     * 赤道半径
     */
    private static double EARTH_RADIUS = 6378.137;


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }



    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FeignService feignService;


    /**
     * 将三方数据写入mongo
     *
     * @param result 定位数据
     * @return 定位数据
     */
    @Override
    public boolean addMongoData(List<MapLocation> result, String userId) {
        if(CollectionUtils.isEmpty(result) || StringUtils.isEmpty(userId)){
            throw new BussException("缺少入参");
        }
        UserInfoVo userInfoVo = feignService.getUserById(userId);
        for(MapLocation mapLocationEntity : result){
            mapLocationEntity.setUserId(userId);
            mapLocationEntity.setLocationTime(LocalDateTime.now());
            if(null != userInfoVo){
                mapLocationEntity.setUserName(userInfoVo.getUserCode());
                mapLocationEntity.setPhoneNo(userInfoVo.getPhoneNo());
            }
            mapLocationEntity.setStatus(1);
        }
        if (!CollectionUtils.isEmpty(result)) {
            mongoTemplate.insertAll(result);
        }
        //放入redis
        RedisUtil.hset(CachePre.DRIVERS_LOCATION_LAST,userId,JSONObject.toJSONString(result.get(0)));
        return true;
    }





    /**
     * 查询车辆轨迹和总里程数
     *
     * @param driversLocationDto
     * @return
     */
    @Override
    public TrailVo queryLocationList(DriversLocationDto driversLocationDto) {
        TrailVo trailVo = new TrailVo();
        Map<String,List<MapLocation>> map = new HashMap<>();
        for(LocationDto dto : driversLocationDto.getList()){
            Query query = new Query();
            if (!StringUtils.isEmpty(dto.getUserId())) {
                query.addCriteria(new Criteria().where("userId").is(dto.getUserId()));
            }
            if (null != dto.getStartTime() && null != dto.getEndTime()) {
                query.addCriteria(new Criteria().where("locationTime").gte(dto.getStartTime()).lte(dto.getEndTime()));
            }
            Criteria state = new Criteria().where("status").is(1);
            query.addCriteria(state);
            query.with(Sort.by(Sort.Order.desc("location_time"))); //排序逻辑
            List<MapLocation> list = mongoTemplate.find(query, MapLocation.class);
            //排序
            list.sort((MapLocation m1, MapLocation m2) -> m2.getLocationTime().compareTo(m1.getLocationTime()));
            map.put(dto.getUserId(),list);
        }
        trailVo.setMap(map);
        return trailVo;
    }





}
