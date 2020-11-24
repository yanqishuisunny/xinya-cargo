package com.cargo.area.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.area.entity.SysdicEntity;
import com.cargo.area.mapper.SysdicMapper;
import com.cargo.area.service.ISysdicService;
import com.cargo.area.vo.SysDicVo;
import com.commom.cache.Constant;
import com.commom.core.BeanConverter;
import com.google.common.base.Splitter;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author 何立辉
 * @since 2019-09-04
 */
@Service
public class SysdicServiceImpl extends ServiceImpl<SysdicMapper, SysdicEntity> implements ISysdicService {

    @Override
    public Map<String, List<SysdicEntity>> selectItemMap(String codes) {
        List<String> listCode = Splitter.on(",").trimResults().splitToList(codes);
        QueryWrapper<SysdicEntity> qw = new QueryWrapper<>();
        qw.in("dic_code", listCode);
        qw.eq("status", Constant.Status.ENABLE);
        qw.ne("parent_id", 0);

        List<SysdicEntity> listDic = this.list(qw);

        Map<String, List<SysdicEntity>> dicMap = new HashMap<>();
        for (String code : listCode) {
            List<SysdicEntity> itemList = new ArrayList<>();
            for (SysdicEntity dic : listDic) {
                if (Objects.equals(code, dic.getDicCode())) {
                    itemList.add(dic);
                }
            }
            dicMap.put(code, itemList);
            listDic.removeAll(itemList);
        }
        return dicMap;
    }

    @Override
    public List<SysdicEntity> dicData(String dicCode) {
        QueryWrapper<SysdicEntity> qw = new QueryWrapper<>();
        qw.eq("dic_code", dicCode);
        qw.eq("status", 1);
        qw.ne("parent_id", "0");
        qw.orderByAsc("order_num");
        return baseMapper.selectList(qw);
    }

    @Override
    public Map<String,List<SysdicEntity>> allData(){
        QueryWrapper<SysdicEntity> qw = new QueryWrapper<>();
        qw.eq("status", 1);
        qw.ne("parent_id", "0");
        List<SysdicEntity> list = baseMapper.selectList(qw);
        Map<String,List<SysdicEntity>>  map = new HashMap<>();
        //下面开始分组，按照dic_code进行分组
        if(list != null && list.size() > 0){
            for(SysdicEntity entity : list){
                String key = entity.getDicCode();
                List<SysdicEntity> entityList = map.get(key);
                if(entityList == null || entityList.size() == 0){
                    entityList = new ArrayList<>();
                    map.put(key,entityList);
                }
                entityList.add(entity);
            }
        }
        return map;
    }

    /**
     * 查询出热门城市
     * @return
     */
    @Override
    public List<SysDicVo> selectHeatCity() {
        QueryWrapper<SysdicEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("dic_code","heatCity");
        wrapper.eq("status",1);
        wrapper.eq("is_able",1);
        //查询出热门城市
        List<SysdicEntity> heatCityList = baseMapper.selectList(wrapper);
        List<SysDicVo> sysDicVoList=new ArrayList<>();
        SysDicVo sysDicVo;
        /*for(SysdicEntity entity:heatCityList){
            sysDicVo=new SysDicVo();
            sysDicVo.setDicCode(entity.getDicCode());
            sysDicVo.setDicName(entity.getDicName());
            sysDicVo.setDicValue(entity.getDicValue());
            sysDicVo.setOrderNum(entity.getOrderNum());
            sysDicVo.setFlgDefault(entity.getFlgDefault());
            sysDicVo.setStatus(entity.getStatus());
            sysDicVo.setParentId(entity.getParentId());
            sysDicVo.setId(entity.getId());
            sysDicVoList.add(sysDicVo);
        }*/
        return BeanConverter.convert(SysDicVo.class,heatCityList);

    }

}
