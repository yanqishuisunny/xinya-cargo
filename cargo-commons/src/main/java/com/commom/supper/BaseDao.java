package com.commom.supper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T> extends BaseMapper<T> {
}
