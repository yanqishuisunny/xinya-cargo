package com.commom.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.commom.cache.Constant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author kingJing
 * @Description: mybatis-plus 配置
 * @Date: 2019/7/5 13:38
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    private final String gmtCreate = "gmtCreate";//创建时间
    private final String gmtModified = "gmtModified";//修改时间
    private final String isAble = "isAble";//是否删除


    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(gmtCreate, LocalDateTime.now(), metaObject);
        this.setFieldValByName(gmtModified, LocalDateTime.now(), metaObject);
        this.setFieldValByName(isAble, Constant.AbleEnum.YES.getValue(), metaObject);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(gmtModified, LocalDateTime.now(), metaObject);
    }

}
