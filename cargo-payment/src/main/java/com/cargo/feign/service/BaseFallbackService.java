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
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Slf4j
public class BaseFallbackService implements BaseFeignService {

    @Override
    public ResponseInfo<IPage<DriverOrgEntity>> pcList(DriverOrgDto dto) {
        log.error("--------------------FeignClient--启动熔断:{}" , "IPage<DriverOrgEntity>");
        return null;
    }

    @ApiOperation(value = "承运商详情")
    @PostMapping("/detailByCarrierId")
    public ResponseInfo<CarrierdetailVo> selectCarrierByRoleId(@RequestBody String id) {
        log.error("--------------------FeignClient--启动熔断:{}" , "CarrierdetailVo");
        return null;
    }

    @ApiOperation(value = "获取货主企业信息")
    @PostMapping("/org/get/{orgId}")
    public OrgEntity getOrgById(@PathVariable String orgId) {
        log.error("--------------------FeignClient--启动熔断:{}" , "OrgEntity");
        return null;
    }

    @PostMapping("/list?size=10000")
    @ApiOperation(value = "运单集合")
    @Transactional
    public ResponseInfo<Page<WaybillVo>> list(@RequestBody WaybillDto dto){
        log.error("--------------------FeignClient--启动熔断:{}" , "Page<WaybillVo>");
        return null;
    }

    @Override
    public UserInfoEntity findById(String userId) {
        log.error("--------------------FeignClient--启动熔断:{}" , "userId");
        return null;
    }
}