package com.cargo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.account.dto.AccountOrderListDto;
import com.cargo.account.entity.AccountOrderEntity;
import com.cargo.account.vo.AccountOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 帐目表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Repository
public interface AccountOrderMapper extends BaseMapper<AccountOrderEntity> {

    List<AccountOrderVo> accountOrderMapperList(@Param("dto") AccountOrderListDto dto, Page<AccountOrderVo> page);
}
