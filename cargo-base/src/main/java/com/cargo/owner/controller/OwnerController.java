package com.cargo.owner.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.owner.dto.OwnerDto;
import com.cargo.owner.entity.OwnerEntity;
import com.cargo.owner.service.OwnerService;
import com.cargo.owner.vo.OwnerVo;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.OrgUserAssociationEntity;
import com.cargo.user.service.OrgService;
import com.cargo.user.service.OrgUserAssociationService;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.vo.DriverInformationVo;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.BaseEntity;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 货主
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@RestController
@Api(tags = "货主基础信息")
@RequestMapping("/api/base/owner")
public class OwnerController extends SuperController {


    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgUserAssociationService orgUserAssociationService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 新增方法
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public ResponseInfo<String> add(@RequestBody OwnerDto t) {

        if (ObjectUtils.isEmpty(t.getLicenseOrgName())) {
            throw new BussException("货主名称不能为空！");
        }
        QueryWrapper<OwnerEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("org_name",t.getLicenseOrgName());
        List<OwnerEntity> list = ownerService.list(queryWrapper);
        if (!ObjectUtils.isEmpty(list)) {
            throw  new BussException("货主名称已存在！");
        }
        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setOrgName(t.getLicenseOrgName());
        orgEntity.setOrgRole(1);
        orgEntity.setOrgType(0);
        orgEntity.setOverdueStatus(0);
        orgEntity.setOrgSource(0);
        orgEntity.setBizType(0);
        orgEntity.setUserId(ShiroUtil.getUserId());
        orgEntity.setAuditStatus(0);
        orgEntity.setExtAuditStatus(0);
        orgService.save(orgEntity);
        String s = ShiroUtil.currentUserId();
        OwnerEntity convert = BeanConverter.convert(OwnerEntity.class, t);
        convert.setOrgId(orgEntity.getOrgId());
        convert.setOrgName(t.getLicenseOrgName());
        //保存
        ownerService.save(convert);
        //保存货主与当前的用户的关系
        OrgUserAssociationEntity o = new OrgUserAssociationEntity();
        o.setUserId(s);
        o.setOrgId( orgEntity.getOrgId());
        o.setOrgRole(String.valueOf(1));
        orgUserAssociationService.save(o);
        return ResponseUtil.success(orgEntity.getOrgId());
    }


    /**
     * 修改方法
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public ResponseInfo update(@RequestBody OwnerDto t) {
        if (ObjectUtils.isEmpty(t.getOwnerId())) {
            throw new BussException("修改参数不能为空!");
        }
        OwnerEntity byId = ownerService.getById(t.getOwnerId());
        if (ObjectUtils.isEmpty(byId)) {
            throw new BussException("当前货主不存在!");
        }
        OwnerEntity convert = BeanConverter.convert(OwnerEntity.class, t);
        //保存
        ownerService.updateById(convert);
        return ResponseUtil.success();
    }

//    /**
//     * 详情
//     */
//    @ApiOperation(value = "详情")
//    @GetMapping("/detail/{id}")
//    public ResponseInfo<OwnerVo> detail(@PathVariable String id) {
//        if (ObjectUtils.isEmpty(id)) {
//            return ResponseUtil.success();
//        }
//        OwnerEntity byId = ownerService.getById(id);
//        return ResponseUtil.success(BeanConverter.convert(OwnerVo.class,byId));
//    }

    @ApiOperation(value = "详情")
    @GetMapping("/detail/{orgId}")
    public ResponseInfo<OwnerVo> detailByOrgId(@PathVariable String orgId) {
        if (ObjectUtils.isEmpty(orgId)) {
            return ResponseUtil.success();
        }
        QueryWrapper<OwnerEntity> ownerEntityQueryWrapper = new QueryWrapper<>();
        ownerEntityQueryWrapper.eq("org_id",orgId);
        OwnerEntity one = ownerService.getOne(ownerEntityQueryWrapper);
        return ResponseUtil.success(BeanConverter.convert(OwnerVo.class,one));
    }

    /**
     * 校验
     */
    @ApiOperation(value = "校验")
    @PostMapping("/check")
    public ResponseInfo check(@RequestBody OwnerDto t) {
        QueryWrapper<OwnerEntity> queryWrapper = new QueryWrapper();
        if (!ObjectUtils.isEmpty(t.getOrgName())) {
            queryWrapper.eq("org_name",t.getOrgName());
        }
        if (!ObjectUtils.isEmpty(t.getOwnerId())) {
            queryWrapper.ne("owner_id",t.getOwnerId());
        }
        List<OwnerEntity> list = ownerService.list(queryWrapper);
        if (ObjectUtils.isEmpty(list)) {
            return ResponseUtil.success(true);
        }
        return ResponseUtil.success(false);
    }



   /* @ApiOperation(value = "获取审核货主信息列表")
    @PostMapping("/examineList")
    public ResponseInfo<Page<OwnerVo>> examineList(@RequestBody OwnerDto dto) {
        Page<OwnerVo> page = this.getPage(false);
        return ResponseUtil.success(ownerService.queryForExamineList(dto,page));
    }

    @ApiOperation(value = "审核货主状态变更")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody OwnerDto dto) {
        return ResponseUtil.result(ownerService.editStatus(dto));
    }*/
}
