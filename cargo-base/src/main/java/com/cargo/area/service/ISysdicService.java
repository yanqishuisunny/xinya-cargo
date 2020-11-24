package com.cargo.area.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.area.entity.SysdicEntity;
import com.cargo.area.vo.SysDicVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author 何立辉
 * @since 2019-09-04
 */
public interface ISysdicService extends IService<SysdicEntity> {

    /**
     * 根据字典code 查询字典
     * @param codes
     * @return
     */
    Map<String, List<SysdicEntity>> selectItemMap(String codes);

    List<SysdicEntity> dicData(String dicCode);

    Map<String,List<SysdicEntity>> allData();

    List<SysDicVo> selectHeatCity();


}
