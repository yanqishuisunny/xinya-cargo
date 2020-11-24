package com.cargo.feign.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.feign.dto.DriverOrgDto;
import com.cargo.feign.dto.WaybillDto;
import com.cargo.feign.entity.DriverOrgEntity;
import com.cargo.feign.entity.OrgEntity;
import com.cargo.feign.entity.UserInfoEntity;
import com.cargo.feign.vo.CarrierdetailVo;
import com.cargo.feign.vo.WaybillVo;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: xinzs
 * @Date: 2020/11/17 17:38
 */
@Component
@FeignClient(value = "xinya-base")
public interface BaseFeignService {


    @PostMapping("/api/base/driver/pc-list")
    ResponseInfo<IPage<DriverOrgEntity>> pcList(@RequestBody DriverOrgDto dto);

    @ApiOperation(value = "承运商详情")
    @PostMapping("/detailByCarrierId")
    ResponseInfo<CarrierdetailVo> selectCarrierByRoleId(@RequestBody String id);

    @ApiOperation(value = "获取货主企业信息")
    @PostMapping("/org/get/{orgId}")
    public OrgEntity getOrgById(@PathVariable String orgId);

    @PostMapping("/list?size=10000")
    @ApiOperation(value = "运单集合")
    @Transactional
    public ResponseInfo<Page<WaybillVo>> list(@RequestBody WaybillDto dto);

    @ApiOperation(value = "根据ID获得用户")
    @GetMapping("/userInfo/get/{userId}")
    public UserInfoEntity findById(@ApiParam("用户ID") @PathVariable("userId") String userId);
}
