package com.cargo.evaluates.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.complaint.entity.UserInfoEntity;
import com.cargo.evaluates.dto.ExceptDto;
import com.cargo.evaluates.dto.FileDto;
import com.cargo.evaluates.entity.ExceptEntity;
import com.cargo.evaluates.entity.FileEntity;
import com.cargo.evaluates.service.ExceptService;
import com.cargo.evaluates.service.impl.FileService;
import com.cargo.evaluates.vo.ExceptVo;
import com.cargo.evaluates.vo.FileVo;
import com.cargo.feign.service.FeignService;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 异常单据表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/api/evaluate/except")
public class ExceptController extends SuperController {


    @Autowired
    private ExceptService exceptService;


    @Autowired
    private FileService fileService;


    @Autowired
    private FeignService feignService;

    @PostMapping("/add")
    @ApiOperation(value = "新增异常单据")
    @Transactional
    public ResponseInfo add(@RequestBody ExceptDto t) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        t.setCreateUserId(byId.getUserId());
        t.setCreateUserName(byId.getUserName());
        t.setCreateUserPhone(byId.getPhoneNo());
        t.setOrgId(orgId);
        t.setExceptStatus(0);
        t.setExceptNo(SnowflakeIdWorker.generateId().toString());
//        t.setOrgName();
        ExceptEntity convert = BeanConverter.convert(ExceptEntity.class, t);

        this.exceptService.save(convert);
        //保存图片
        if (!ObjectUtils.isEmpty( t.getFiles())) {
               for (FileDto fileDto: t.getFiles()){
                   fileDto.setRelatId(convert.getExceptId());
                   fileDto.setFileType(1);
               }
            fileService.saveBatch(BeanConverter.convert(FileEntity.class,t.getFiles()));
        }
        return ResponseUtil.success();
    }

    @PostMapping("/edit")
    @ApiOperation(value = "修改异常单据")
    @Transactional
    public ResponseInfo edit(@RequestBody ExceptDto t) {
        exceptService.updateById(BeanConverter.convert(ExceptEntity.class,t));
        if (!ObjectUtils.isEmpty(t.getFileId())) {
            fileService.removeByIds(t.getFileId());
        }
        //保存照片
        if (!ObjectUtils.isEmpty(t.getFiles())) {
            fileService.saveOrUpdateBatch(BeanConverter.convert(FileEntity.class,t.getFiles()));
        }
        return ResponseUtil.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "撤销")
    public ResponseInfo delete(@RequestParam String exceptId) {
        exceptService.removeById(exceptId);
        return ResponseUtil.success();
    }

    @DeleteMapping("/deletes")
    @ApiOperation(value = "撤销")
    public ResponseInfo deletes(@RequestBody List<String> exceptIds) {
        exceptService.removeByIds(exceptIds);
        return ResponseUtil.success();
    }
    @GetMapping("/detail")
    @ApiOperation(value = "异常详情")
    public ResponseInfo<ExceptVo> detail(@RequestParam String exceptId) {
        ExceptEntity byId = exceptService.getById(exceptId);

        ExceptVo convert = BeanConverter.convert(ExceptVo.class, byId);


        QueryWrapper<FileEntity> queryWrappers = new QueryWrapper<>();
        queryWrappers.eq("relat_id", convert.getExceptId());
        queryWrappers.eq("file_type", 1);
        queryWrappers.orderByDesc("gmt_create");
        List<FileEntity> entities = fileService.list(queryWrappers);
        convert.setFiles(BeanConverter.convert(FileVo.class, entities));

        return ResponseUtil.success(convert);
    }
    @PostMapping("/list")
    @ApiOperation(value = "异常列表")
    public ResponseInfo<IPage<ExceptVo>> list(@RequestBody ExceptDto t) {
        IPage<ExceptEntity> page = this.getPage(true);
        QueryWrapper<ExceptEntity> queryWrapper = new QueryWrapper<>();
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        byId.setOrgId(orgId);
        if (!ObjectUtils.isEmpty(t.getRelatNo())) {
            queryWrapper.like("relat_no",t.getRelatNo());
        }
        if (!ObjectUtils.isEmpty(t.getExceptStatus())) {
            queryWrapper.eq("except_status",t.getExceptStatus());
        }
        if (!ObjectUtils.isEmpty(t.getExceptType())) {
            queryWrapper.eq("except_type",t.getExceptType());
        }
        if (!ObjectUtils.isEmpty(t.getGmtCreateState())) {
            queryWrapper.gt("gmt_create",t.getGmtCreateState());
        }
        if (!ObjectUtils.isEmpty(t.getGmtCreateEnd())) {
            queryWrapper.lt("gmt_create",t.getGmtCreateEnd());
        }
        if (!ObjectUtils.isEmpty(byId.getOrgId())) {
            queryWrapper.eq("org_id",byId.getOrgId());
        }
        queryWrapper.orderByDesc("gmt_create");
        IPage<ExceptEntity> iPage = exceptService.page(page, queryWrapper);
        IPage<ExceptVo> convert = BeanConverter.convert(ExceptVo.class, iPage);
        if (!ObjectUtils.isEmpty(convert)) {
            if (!ObjectUtils.isEmpty(convert.getRecords())) {
                for (ExceptVo exceptEntity: convert.getRecords() ) {
                    QueryWrapper<FileEntity> queryWrappers = new QueryWrapper<>();
                    queryWrappers.eq("relat_id",exceptEntity.getExceptId());
                    queryWrappers.eq("file_type",1);
                    queryWrappers.orderByDesc("gmt_create");
                    List<FileEntity> entities = fileService.list(queryWrappers);
                    exceptEntity.setFiles(BeanConverter.convert(FileVo.class,entities));
                }
            }
        }
        return ResponseUtil.success(convert);
    }

}
