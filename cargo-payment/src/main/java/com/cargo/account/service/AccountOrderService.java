package com.cargo.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.account.dto.AccountOrderDto;
import com.cargo.account.dto.AccountOrderListDto;
import com.cargo.account.entity.AccountOrderEntity;
import com.cargo.account.vo.AccountOrderDetailVo;
import com.cargo.account.vo.AccountOrderVo;

import java.util.List;

/**
 * <p>
 * 帐目表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
public interface AccountOrderService extends IService<AccountOrderEntity> {

    /**
     * 账目列表
     * @param dto
     * @param page
     * @return
     */
    Page<AccountOrderVo> accountOrderMapperList(AccountOrderListDto dto, Page<AccountOrderVo> page);


    /**
     * 锁帐
     * @param dto
     */
    public void updateAccountOrder(AccountOrderDto dto);

    /**
     * 查看明细
     * @param dto
     * @return
     */
    public AccountOrderDetailVo accountOrderDetail(AccountOrderDto dto);

}
