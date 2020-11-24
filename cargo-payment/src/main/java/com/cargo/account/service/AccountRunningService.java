package com.cargo.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.account.dto.AccountRunningDto;
import com.cargo.account.entity.AccountRunningEntity;
import com.cargo.account.vo.AccountRunningVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 帐目流水表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
public interface AccountRunningService extends IService<AccountRunningEntity> {

    public List<AccountRunningVo> accountRunning();

    /**
     * 最新可开票金额
     * @return
     */
    public BigDecimal getInvoiceAmount();
}
