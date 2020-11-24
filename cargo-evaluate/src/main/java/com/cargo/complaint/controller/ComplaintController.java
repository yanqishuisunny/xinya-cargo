package com.cargo.complaint.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cargo.complaint.dto.ComplaintDto;
import com.cargo.complaint.entity.ComplaintEntity;
import com.cargo.complaint.entity.ComplaintLogEntity;
import com.cargo.complaint.entity.UserInfoEntity;
import com.cargo.complaint.service.ComplaintLogService;
import com.cargo.complaint.service.ComplaintService;
import com.cargo.complaint.vo.ComplaintLogVo;
import com.cargo.complaint.vo.ComplaintVo;
import com.cargo.feign.service.FeignService;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
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
 * 投诉表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-06
 */
@Api(tags = "投诉表")
@RestController
@RequestMapping("/api/evaluate/complaint")
public class ComplaintController extends SuperController {

    @Autowired
    ComplaintService complaintService;

    @Autowired
    ComplaintLogService complaintLogService;

    @Autowired
    private FeignService feignService;

    @PostMapping("/add")
    @ApiOperation(value = "新增投诉")
    public ResponseInfo complaintAdd(@RequestBody ComplaintDto complaintDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        complaintDto.setOrgId(orgId);
        complaintDto.setCreateId(byId.getUserId());
        complaintDto.setCreateName(byId.getUserName());
        complaintDto.setCreatePhone(byId.getPhoneNo());
        //订单信息
        ComplaintEntity convert = BeanConverter.convert(ComplaintEntity.class, complaintDto);
        convert.setComplaintStatus(1);
        complaintService.save(convert);
        ComplaintLogEntity complaintLogEntity = new ComplaintLogEntity();
        complaintLogEntity.setComplaintId(convert.getComplaintId());
        complaintLogEntity.setComplaintStatus(complaintDto.getComplaintStatus());
        complaintLogService.save(complaintLogEntity);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public ResponseInfo update(@RequestBody ComplaintDto complaintDto) {
        //修改当条状态 和放入处理log
        String s = ShiroUtil.currentUserId();
        ComplaintEntity convert = BeanConverter.convert(ComplaintEntity.class, complaintDto);
        complaintService.updateById(convert);
        ComplaintLogEntity complaintLogEntity = new ComplaintLogEntity();
        complaintLogEntity.setComplaintId(convert.getComplaintId());
        complaintLogEntity.setComplaintStatus(complaintDto.getComplaintStatus());
        //放入处理结果
        complaintLogEntity.setComplaintTxt(complaintDto.getConductorTxt());
        complaintLogService.save(complaintLogEntity);
        return ResponseUtil.success();
    }


    @PostMapping("/list")
    @ApiOperation(value = "投诉列表")
    public ResponseInfo<IPage<ComplaintVo>> list(@RequestBody ComplaintDto complaintDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        byId.setOrgId(orgId);
        IPage<ComplaintEntity> page = this.getPage(true);
        QueryWrapper<ComplaintEntity> q = new QueryWrapper();
        if (!ObjectUtils.isEmpty(complaintDto.getDocumentNumber())) {
            q.like("document_number",complaintDto.getDocumentNumber());
        }

        if (!ObjectUtils.isEmpty(complaintDto.getCreatePhone())) {
            q.like("create_phone",complaintDto.getCreatePhone());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getComplaintStatus())) {
            q.eq("complaint_status",complaintDto.getComplaintStatus());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getGmtCreateStart())) {
            q.ge("gmt_create",complaintDto.getGmtCreateStart());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getGmtCreateEnd())) {
            q.le("gmt_create",complaintDto.getGmtCreateEnd());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getOrgName())) {
            q.like("org_name",complaintDto.getOrgName());
        }
        if (!ObjectUtils.isEmpty(byId.getOrgId())) {
            q.eq("org_id",byId.getOrgId());
        }
        q.orderByDesc("gmt_create");
        IPage<ComplaintEntity> entityIPage = complaintService.page(page,q);
        IPage<ComplaintVo> convert = BeanConverter.convert(ComplaintVo.class, entityIPage);
        return ResponseUtil.success(convert);
    }


    @PostMapping("/listByOrder")
    @ApiOperation(value = "投诉列表承运商处")
    public ResponseInfo<IPage<ComplaintVo>> listByOrder(@RequestBody ComplaintDto complaintDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = null;
        IPage<ComplaintEntity> page = this.getPage(true);
        QueryWrapper<ComplaintEntity> q   = new QueryWrapper();
        if (!ObjectUtils.isEmpty(complaintDto.getDocumentNumber())) {
            q.like("document_number",complaintDto.getDocumentNumber());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getGmtCreateStart())) {
            q.ge("gmt_create",complaintDto.getGmtCreateStart());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getGmtCreateEnd())) {
            q.le("gmt_create",complaintDto.getGmtCreateEnd());
        }
        if (!ObjectUtils.isEmpty(complaintDto.getOrgName())) {
            q.like("orgId",complaintDto.getOrgName());
        }
        q.like("org_id",orgId);
        q.orderByDesc("gmt_create");
        IPage<ComplaintEntity> entityIPage = complaintService.page(page,q);
        //todo  从订单中补全信息
        IPage<ComplaintVo> convert = BeanConverter.convert(ComplaintVo.class, entityIPage);
        return ResponseUtil.success(convert);
    }

    @GetMapping("/detailByOrder")
    @ApiOperation(value = "详情")
    public ResponseInfo<ComplaintVo> detailByOrder(@RequestParam String complaintId) {
        if (ObjectUtils.isEmpty(complaintId)) {
            return ResponseUtil.success();
        }
        String s = ShiroUtil.currentUserId();
        ComplaintEntity byId = complaintService.getById(complaintId);
        ComplaintVo convert = BeanConverter.convert(ComplaintVo.class, byId);
        if (!ObjectUtils.isEmpty(byId)) {
            //查找状态更改历史
            QueryWrapper<ComplaintLogEntity> qq = new QueryWrapper();
            qq.eq("complaint_id",byId);
            qq.orderByDesc("gmt_create");
            List<ComplaintLogEntity> list = complaintLogService.list(qq);
            convert.setComplaintLogVos(BeanConverter.convert(ComplaintLogVo.class,list));
        }
        //todo  从订单中补全信息
        return ResponseUtil.success(convert);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情")
    public ResponseInfo<ComplaintVo> detail(@RequestParam String complaintId) {
        if (ObjectUtils.isEmpty(complaintId)) {
            return ResponseUtil.success();
        }
        String s = ShiroUtil.currentUserId();
        ComplaintEntity byId = complaintService.getById(complaintId);
        ComplaintVo convert = BeanConverter.convert(ComplaintVo.class, byId);
        if (!ObjectUtils.isEmpty(byId)) {
              //查找状态更改历史
            QueryWrapper<ComplaintLogEntity> qq = new QueryWrapper();
            qq.eq("complaint_id",byId);
            qq.orderByDesc("gmt_create");
            List<ComplaintLogEntity> list = complaintLogService.list(qq);
            convert.setComplaintLogVos(BeanConverter.convert(ComplaintLogVo.class,list));
        }
        return ResponseUtil.success(convert);
    }
}

