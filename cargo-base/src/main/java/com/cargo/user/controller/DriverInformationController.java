package com.cargo.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.service.DriverInformationService;
import com.cargo.user.vo.DriverInformationVo;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 司机信息表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Api(tags = "司机信息表")
@RestController
@RequestMapping("/api/base/driverInformation")
public class DriverInformationController extends SuperController {


    @Autowired
    private DriverInformationService driverInformationService;


    @PostMapping("/edit")
    @ApiOperation(value = "1502-修改-新增司机信息")
    public ResponseInfo edit(@RequestBody DriverInformationDto dto) {
        String userId = ShiroUtil.currentUserId();
        return ResponseUtil.success(driverInformationService.editDriver(userId, dto));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "1502-获取司机信息")
    public ResponseInfo<DriverInformationEntity> detail() {
        return ResponseUtil.success(driverInformationService.selectInfoByUserId(ShiroUtil.currentUserId()));
    }

    @ApiOperation(value = "获取审核司机信息列表")
    @PostMapping("/examineList")
    public ResponseInfo<Page<DriverInformationVo>> examineList(@RequestBody DriverInformationDto dto) {
        Page<DriverInformationVo> page = this.getPage(false);
        return ResponseUtil.success(driverInformationService.queryForExamineList(dto,page));
    }

    @ApiOperation(value = "审核司机状态变更")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody DriverInformationDto dto) {
        return ResponseUtil.result(driverInformationService.editStatus(dto));
    }


}

