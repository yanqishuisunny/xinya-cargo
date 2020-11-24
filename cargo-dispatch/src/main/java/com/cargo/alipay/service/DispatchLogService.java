package com.cargo.alipay.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.dto.DispatchLogDto;
import com.cargo.entity.DispatchLog;
import com.cargo.vo.BannerCarVo;

import java.util.List;

/**
 * <p>
 * 调度单表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
public interface DispatchLogService extends IService<DispatchLog> {

    /**
     * 司机的甘蔗图
     * @param dispatchLogDto
     * @param orgId
     * @return
     */
    List<BannerCarVo> carBannerByDriver(DispatchLogDto dispatchLogDto, String orgId);

    /**
     * 车辆的甘蔗图
     * @param dispatchLogDto
     * @param orgId
     * @return
     */
    List<BannerCarVo> carBannerByCar(DispatchLogDto dispatchLogDto, String orgId);
}
