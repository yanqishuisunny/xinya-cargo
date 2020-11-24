package com.cargo.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.common.UserEnum;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.entity.DriverOrgEntity;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.OrgUserAssociationEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.DriverOrgMapper;
import com.cargo.user.service.OrgService;
import com.cargo.user.service.OrgUserAssociationService;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.service.UserLoginService;
import com.cargo.user.vo.DriverInformationVo;
import com.cargo.user.vo.LoginOrgUserVo;
import com.cargo.user.vo.OrgVo;
import com.commom.cache.CachePre;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Api(tags = "企业-组织controller")
@RestController
@RequestMapping("/api/base/org")
public class OrgController extends SuperController {


    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgUserAssociationService orgUserAssociationService;


    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private UserLoginService userLoginService;


    @Autowired
    private DriverOrgMapper driverOrgMapper;


    @ApiOperation(value = "查询当前登陆人对应合作企业")
    @PostMapping("/list")
    @Transactional
    public ResponseInfo<List<OrgEntity>> list(@RequestBody OrgDto dto) {
        String userId = ShiroUtil.getUserId();
        List<OrgEntity> orgList = new ArrayList<>();
        QueryWrapper<OrgUserAssociationEntity> associationWrapper = new QueryWrapper<>();
        associationWrapper.eq("user_id", userId);
        associationWrapper.eq("org_role", this.getHeaders().getVersionType());
        List<OrgUserAssociationEntity> associationEntityList = orgUserAssociationService.list(associationWrapper);
        if (CollectionUtils.isEmpty(associationEntityList)) {
            return ResponseUtil.result(orgList);
        }
        QueryWrapper<OrgEntity> orgWrapper = new QueryWrapper<>();
        orgWrapper.in("org_id", associationEntityList.stream().map(OrgUserAssociationEntity::getOrgId).collect(Collectors.toList()));
        return ResponseUtil.result(orgService.list(orgWrapper));
    }




    @ApiOperation(value = "切换企业")
    @PostMapping("/switchOrg")
    @Transactional
    public ResponseInfo<String> switchOrg(@RequestBody OrgDto dto) {
        if(StringUtil.isEmpty(dto.getOrgId())){
            throw new BussException("缺少入参");
        }
        OrgEntity orgEntity = orgService.getById(dto.getOrgId());
        if(null == orgEntity){
            throw new BussException("没有该企业");
        }
        UserInfoEntity user = userInfoService.getById(ShiroUtil.getUserId());
        if (null == user ) {
            return ResponseUtil.result(UserEnum.Code.NOT_REGISTER);
        }
        LoginOrgUserVo loginOrgUserVo = new LoginOrgUserVo();
        loginOrgUserVo.setOrgId(dto.getOrgId());
        String token = this.userLoginService.login(user,loginOrgUserVo);
        RedisUtil.del(CachePre.LOING_SHIRO_JWT_ID + this.getHeaders().getToken());
        return ResponseUtil.result(token);
    }





    @ApiOperation(value = "获取审核企业信息列表")
    @PostMapping("/examineList")
    public ResponseInfo<Page<OrgVo>> examineList(@RequestBody OrgDto dto) {
        Page<OrgVo> page = this.getPage(false);
        return ResponseUtil.success(orgService.queryForExamineList(dto,page));
    }

    @ApiOperation(value = "审核企业状态变更")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody OrgDto dto) {
        return ResponseUtil.result(orgService.editStatus(dto));
    }

}

