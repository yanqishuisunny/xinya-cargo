package com.cargo.area.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.area.dto.CityDto;
import com.cargo.area.dto.ProvinceDto;
import com.cargo.area.entity.*;
import com.cargo.area.mapper.AreaMapper;
import com.cargo.area.service.AreaService;
import com.cargo.area.service.ISysdicService;
import com.cargo.area.vo.*;
import com.cargo.common.ImageOcrEnum;
import com.cargo.utils.EnglishAndChineseUtil;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.supper.BaseEntity;
import com.commom.utils.RedisUtil;
import com.commom.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何立辉
 * @since 2019-07-25
 */
@Service
@Slf4j
public class AreaServiceImpl extends ServiceImpl<AreaMapper, AreaEntity> implements AreaService {

    @Autowired
    private ISysdicService sysdicService;



    @Override
    public List<AreaEntity> cascadeQuery(String areaLevel, String parentId) {
        QueryWrapper<AreaEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("area_level", Integer.parseInt(areaLevel));
        wrapper.eq("parent_id", Integer.parseInt(parentId));
        wrapper.eq("is_able", 1);
        List<AreaEntity> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public AreaInfoVo getInfoById(String id) {
        AreaEntity entity = baseMapper.selectById(id);
        int level = entity.getLevel();
        AreaInfoVo vo = new AreaInfoVo();
        vo.setCode(entity.getCode());
        vo.setGbtCode(entity.getGbtCode());
        vo.setLevel(level);
        vo.setId(entity.getId());
        switch (level) {
            case 4:
                vo.setProvinceId(entity.getId());
                vo.setProvinceName(entity.getName());
                break;
            case 5:
                AreaEntity province = baseMapper.selectById(entity.getParentId());
                vo.setProvinceId(province.getId());
                vo.setProvinceName(province.getName());
                vo.setCityId(entity.getId());
                vo.setCityName(entity.getName());
                break;
            case 6:
                vo.setCountyId(entity.getId());
                vo.setCountyName(entity.getName());
                AreaEntity city = baseMapper.selectById(entity.getParentId());
                vo.setCityId(city.getId());
                vo.setCityName(city.getName());
                province = baseMapper.selectById(city.getParentId());
                vo.setProvinceId(province.getId());
                vo.setProvinceName(province.getName());
                break;
            default:
                log.debug("行政区域等级 {} 无效", level);
        }
        return vo;
    }

    @Override
    public AreCodeEntry getByAreaId(String areaId) {
        AreCodeEntry areCodeEntry = new AreCodeEntry();
        if (ObjectUtils.isEmpty(areaId)) {
            return null;
        }
        if (Integer.parseInt(ImageOcrEnum.DANGER.getType()) != areaId.length()) {
            throw new BussException("请输入正确的区域代码");
        }
        if(areaId!=null&&areaId.length()>=6){
            AreaEntity townEntity = baseMapper.selectById(areaId);
            if (!ObjectUtils.isEmpty(townEntity)) {
                areCodeEntry.setTown(townEntity.getName());
                areCodeEntry.setTownCode(townEntity.getCode());
            }
        }

        String area = areaId.substring(0, 6);
        AreaEntity areaEntity = baseMapper.selectById(area);
        if (!ObjectUtils.isEmpty(areaEntity)) {
            areCodeEntry.setArea(areaEntity.getName());
            areCodeEntry.setAreaCode(areaEntity.getCode());
        }
        String province = areaId.substring(0, 2);
        AreaEntity provinceEntry = baseMapper.selectById(province);
        if (!ObjectUtils.isEmpty(provinceEntry)) {
            areCodeEntry.setProvince(provinceEntry.getName());
            areCodeEntry.setProvinceCode(provinceEntry.getCode());
        }
        //针对于 120000  类型的  默认城市为省会城市
        String substring = areaId.substring(0, 4);
        String sub = substring.substring(2, 4);
        if ("00".equals(sub)) {
            substring = province + "01";
        }
        AreaEntity cityEntity = baseMapper.selectById(substring);
        if (!ObjectUtils.isEmpty(cityEntity)) {
            areCodeEntry.setCity(cityEntity.getName());
            areCodeEntry.setCityCode(cityEntity.getCode());
        }
        return areCodeEntry;
    }

    /**
     * 查询出热门城市
     *
     * @return
     */
    @Override
    public List<SysDicVo> selectHeatCity() {
        QueryWrapper<SysdicEntity> wrapper = Wrappers.<SysdicEntity>query()
                .eq("dic_code", "heatCity")
                .eq("status", 1)
                .eq("is_able", 1)
                .orderByAsc("order_num");
        //查询出热门城市
        List<SysdicEntity> heatCityList = sysdicService.list(wrapper);
        return BeanConverter.convert(SysDicVo.class, heatCityList);

    }

    @Override
    public void saveArea(AreaEntity entity) {
        baseMapper.insert(entity);
//        areaToRedis();
    }

    @Override
    public void editArea(AreaEntity entity) {
        baseMapper.updateById(entity);
//        areaToRedis();
    }

    @Async
    public void areaToRedis(){
        QueryWrapper<AreaEntity> qw = new QueryWrapper<>();
//        qw.in("area_level", 4, 5, 6);
        List<AreaEntity> list = baseMapper.selectList(qw);
        if (!CollectionUtils.isEmpty(list)) {
            List<AreaVo> listVo = BeanConverter.convert(AreaVo.class, list);
            List<AreaVo> vos = listVo.stream().filter(n -> Objects.equals("0", n.getParentId())).map(e -> TreeUtils.findChildren(e, listVo)).collect(Collectors.toList());
            System.out.println("数据："+ JSONObject.toJSON(vos));
            RedisUtil.set("saveOrEdit:area", JSONObject.toJSON(vos));
        }
    }


    /**
     * 查询省份和城市
     *
     * @return
     */
    @Override
    public ProvinceVo selectProvince() {
        LambdaQueryWrapper<AreaEntity> wrapper = Wrappers.<AreaEntity>lambdaQuery()
                .eq(BaseEntity::getIsAble, 1)
                .in(AreaEntity::getLevel, 4, 5);
        //查出所有的省和市
        List<AreaEntity> areaEntityList = this.list(wrapper);
        //取出所有的省份
        List<AreaEntity> provinceList = areaEntityList.stream()
                .filter(areaEntity -> areaEntity.getLevel().equals(4))
                .collect(Collectors.toList());
        //取出对应省份下的所有城市
        List<Province> areaVoProvince = provinceList.stream()
                .map(areaEntity -> BeanConverter.convert(Province.class, areaEntity))
                .collect(Collectors.toList());
        //取出 所有省的首字母 大写， Map<set, List<Map<省，List<城市>>>>
        Set<String> set = areaVoProvince.stream().map(areaVo -> EnglishAndChineseUtil.getHanziInitials(areaVo.getName())).collect(Collectors.toSet());
        List<ProvinceDto> provinceDtoList = new ArrayList<>();
        set.forEach(s -> {
            List<Province> collect = areaVoProvince.stream()
                    .filter(areaVo -> s.equals(EnglishAndChineseUtil.getHanziInitials(areaVo.getName())))
                    .collect(Collectors.toList());
            collect.forEach(province -> {
                List<City> cityList = areaEntityList.stream()
                        .filter(cityEntity -> cityEntity.getParentId().equals(province.getId()))
                        .map(cityEntity -> BeanConverter.convert(City.class, cityEntity))
                        .collect(Collectors.toList());
                ProvinceDto provinceDto = new ProvinceDto();
                provinceDto.setProvince(province);
                provinceDto.setCityList(cityList);
                provinceDtoList.add(provinceDto);
            });
        });
        ProvinceVo provinceVo = new ProvinceVo();
        provinceVo.setEnglish(set);
        provinceVo.setProvinceDtoList(provinceDtoList);
        return provinceVo;
    }

    /**
     * 查询城市
     *
     * @return
     */
    @Override
    public CityVo selectCity() {
        //查询出所有的城市
        LambdaQueryWrapper<AreaEntity> wrapper = Wrappers.<AreaEntity>lambdaQuery()
                .eq(AreaEntity::getIsAble, 1).
                        eq(AreaEntity::getLevel, 5);
        List<AreaEntity> areaEntityList = this.list(wrapper);
        //第一步 取出所有城市的首字母大写 添加到set中
        //第二部  每个字母可能对应多个城市
        //返回的结果是 是一个Map<set，Map<String,List>>
        Set<String> set = areaEntityList.stream().map(areaEntity -> EnglishAndChineseUtil.getHanziInitials(areaEntity.getName())).collect(Collectors.toSet());
        Map<String, List<AreaVo>> map = new HashMap<>();
        List<CityDto> cityDtoList = new ArrayList<>();
        set.forEach(s -> {
            List<City> cityList = areaEntityList.stream()
                    .filter(areaEntity -> s.equals(EnglishAndChineseUtil.getHanziInitials(areaEntity.getName())))
                    .map(areaEntity1 -> BeanConverter.convert(City.class, areaEntity1))
                    .collect(Collectors.toList());
            CityDto cityDto = new CityDto();
            cityDto.setFirstEnglish(s);
            cityDto.setCityList(cityList);
            cityDtoList.add(cityDto);

        });
        CityVo cityVo = new CityVo();
        cityVo.setEnglish(set);
        cityVo.setCityList(cityDtoList);
        return cityVo;
    }

    /**
     * 根据城市名查询
     *
     * @return
     */
    @Override
    public List<City> selectByCityName() {
        LambdaQueryWrapper<AreaEntity> wrapper = Wrappers.<AreaEntity>lambdaQuery()
                .eq(AreaEntity::getIsAble, 1).
                        eq(AreaEntity::getLevel, 5);
        List<AreaEntity> cityList = this.list(wrapper);
        //大写字母筛选，   名称加拼音全拼筛选
        //名称(拼音全称)大写简称
        List<City> cities = cityList.stream().map(areaEntity -> {
            City city = BeanConverter.convert(City.class, areaEntity);
            String dbCityName = areaEntity.getName();
            Optional.ofNullable(dbCityName).ifPresent(s -> {
                String name = "";
                String quancheng = EnglishAndChineseUtil.getHanziPinYinAndFirstUp(s);
                String simpleName = EnglishAndChineseUtil.getHanziToSimpleName(s);
                name = s + "(" + quancheng + ")" + simpleName;
                //areaEntity.setName(name);
                city.setPinyin(quancheng);
                city.setSimpleEnglish(simpleName);
            });
            return city;

        }).collect(Collectors.toList());
        return cities;
    }


}
