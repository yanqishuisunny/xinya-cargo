package com.cargo.area.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.area.entity.AreCodeEntry;
import com.cargo.area.entity.AreaEntity;
import com.cargo.area.entity.City;
import com.cargo.area.vo.AreaInfoVo;
import com.cargo.area.vo.CityVo;
import com.cargo.area.vo.ProvinceVo;
import com.cargo.area.vo.SysDicVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 何立辉
 * @since 2019-07-25
 */
public interface AreaService extends IService<AreaEntity> {

    List<AreaEntity> cascadeQuery(String areaLevel, String parentId);

    /**
     * 根据 id 获取 区域详细信息
     * @param id
     * @return
     */
    AreaInfoVo getInfoById(String id);

    /**
     * 根据AreId 查询区域代码
     * @param areaId
     *
     * @return
     */
    AreCodeEntry getByAreaId(String areaId);

    /**
     * 按照省排序
     * @return
     */
    ProvinceVo selectProvince();



    /**
     * 按照城市排序
     * @return
     */
    CityVo selectCity();


    /**
     * 按照城市查询 （支持中文搜索，拼英搜素，大写字母简写搜素，中文名+（+拼英+）搜素 ）
     * @return
     */

    List<City>  selectByCityName();

    /**
     * 查询热门城市
     */
    List<SysDicVo> selectHeatCity();

    void saveArea(AreaEntity entity);

    void editArea(AreaEntity entity);

}
