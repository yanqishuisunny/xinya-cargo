package com.cargo.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.common.RoleBusCode;
import com.cargo.common.UserEnum;
import com.cargo.core.BaseBusCode;
import com.cargo.user.dto.IdCardVaildDto;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.entity.*;
import com.cargo.user.mapper.MenuRoleMainMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.*;
import com.cargo.user.vo.LoginOrgUserVo;
import com.cargo.user.vo.UserInfoVo;
import com.commom.cache.HeaderDto;
import com.commom.core.AssertBuss;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.utils.DateUtil;
import com.commom.utils.MD5Util;
import com.commom.utils.ResponseUtil;
import com.commom.utils.Tool;
import com.commom.vo.CurrentUser;
import com.google.common.base.Strings;
import com.xsungroup.tms.external.api.BaiduApi;
import com.xsungroup.tms.external.api.PengYuanApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoService {


    @Autowired
    private MenuRoleMainMapper menuRoleMainMapper;


    @Autowired
    private OrgUserAssociationService orgUserAssociationService;

    @Autowired
    private OrgService orgService;


    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PengYuanApi pengYuanApi;

    @Autowired
    private BaiduApi baiduApi;


    @Autowired
    private DriverInformationService driverInformationService;


    @Override
    public UserInfoEntity findUser(String userCode) {
        if(Strings.isNullOrEmpty(userCode)){
            throw new BussException("缺少入参");
        }
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(userCode)) {
            wrapper.eq("user_code", userCode);
        }
        return getOne(wrapper);
    }


    @Override
    public LoginOrgUserVo findUserByCode(String userCode) {
        LoginOrgUserVo loginOrgUserVo = new LoginOrgUserVo();
        if(Strings.isNullOrEmpty(userCode)){
            throw new BussException("缺少入参");
        }
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(userCode)) {
            wrapper.eq("user_code", userCode);
        }
        UserInfoEntity userInfoEntity =  getOne(wrapper);
        if(null == userInfoEntity){
            throw new BussException(UserEnum.Code.NOT_USER);
        }
        loginOrgUserVo.setUserId(userInfoEntity.getUserId());
        loginOrgUserVo.setUserCode(userInfoEntity.getUserCode());
        QueryWrapper<OrgUserAssociationEntity> qw = new QueryWrapper<>();
        qw.eq("user_id",userInfoEntity.getUserId());
        qw.orderByDesc("last_login_time");
        qw.last("limit 1");
        OrgUserAssociationEntity orgUserAssociationEntity = orgUserAssociationService.getOne(qw);
        if(null != orgUserAssociationEntity ){
            loginOrgUserVo.setOrgId(orgUserAssociationEntity.getOrgId());
            OrgEntity orgEntity = orgService.getById(orgUserAssociationEntity.getOrgId());
            if(null != orgEntity){
                loginOrgUserVo.setOrgName(orgEntity.getOrgName());
            }
        }
        return loginOrgUserVo;
    }


    @Override
    public LoginOrgUserVo findUserById(String userId,String versionType) {
        UserInfoEntity userInfoEntity = this.baseMapper.selectById(userId);
        LoginOrgUserVo loginOrgUserVo = new LoginOrgUserVo();
        loginOrgUserVo.setUserId(userId);
        loginOrgUserVo.setUserCode(userInfoEntity.getUserCode());
        QueryWrapper<OrgUserAssociationEntity> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("org_role",versionType);
        qw.orderByDesc("last_login_time");
        qw.last("limit 1");
        OrgUserAssociationEntity orgUserAssociationEntity = orgUserAssociationService.getOne(qw);
        if(null != orgUserAssociationEntity ){
            loginOrgUserVo.setOrgId(orgUserAssociationEntity.getOrgId());
            OrgEntity orgEntity = orgService.getById(orgUserAssociationEntity.getOrgId());
            if(null != orgEntity){
                loginOrgUserVo.setOrgName(orgEntity.getOrgName());
            }
        }
        return loginOrgUserVo;
    }

    @Override
    public Page<UserInfoVo> findByPageUserInfo(UserInfoDto dto, CurrentUser currentUser, Page<UserInfoVo> page,HeaderDto headerDto) {
        if(null == dto){
            throw new BussException("缺少入参");
        }
        List<UserInfoEntity> list = this.baseMapper.findByPageUserInfo(dto,page,currentUser,headerDto.getVersionType());
        List<UserInfoVo> listVo = new ArrayList<>();
        list.stream().forEach(userInfoEntity -> {
            listVo.add(convert(userInfoEntity));
        });
        page.setRecords(listVo);
        return page;
    }




    @Override
    public int editPassword(String userId , String password) {
        AssertBuss.notEmpty(password, BaseBusCode.MISSING_INPUT_PARAMETERS);
        UserInfoEntity userEntity = this.getById(userId);
        AssertBuss.notNull(userEntity, BaseBusCode.ILLEGALARGUMENT);
        userEntity.setSalt(Tool.uuId());
        if (!Strings.isNullOrEmpty(password)) {
            userEntity.setPassword(MD5Util.MD5Encode(password + userEntity.getSalt()));
        }
        int number = this.baseMapper.updateById(userEntity);
        return number;
    }



    @Override
    public UserInfoVo convert(UserInfoEntity userInfoEntity){
        UserInfoVo userInfoVo = BeanConverter.convert(UserInfoVo.class,userInfoEntity);

        //菜单权限
        MenuRoleMainEntity menuRoleMainEntity = menuRoleMainMapper.selectById(userInfoEntity.getMenuRole());
        userInfoVo.setMenuRoleName(menuRoleMainEntity == null ? "":menuRoleMainEntity.getRoleName());
        userInfoVo.setMenuRoleCode(menuRoleMainEntity == null ? "":menuRoleMainEntity.getRoleCode());

        return userInfoVo;
    }



    @Override
    public boolean checkUserCode(String userCode) {
        log.info("手机号校验参数：phoneNo={}", userCode);
        List<UserInfoEntity> userEntityList = this.baseMapper.selectList(new QueryWrapper<UserInfoEntity>().eq("user_code", userCode));
        return userEntityList.size() > 0;
    }


    @Override
    public boolean checkPhone(String phone) {
        log.info("手机号校验参数：phoneNo={}", phone);
        List<UserInfoEntity> userEntityList = this.baseMapper.selectList(new QueryWrapper<UserInfoEntity>().eq("phone_no", phone));
        return userEntityList.size() > 0;
    }


    @Override
    public CurrentUser findCurrentUser(String userId,String orgId) {
        CurrentUser currentUser = this.baseMapper.findCurrentUser(userId,orgId);
        return currentUser;
    }



    @Override
    public void updataUserIdCard(IdCardVaildDto dto) {
        UserInfoEntity user = this.getById(dto.getUserId());
        AssertBuss.notNull(user, BaseBusCode.ILLEGALARGUMENT);
        UserDetailEntity userDetail = dto.convert(UserDetailEntity.class);
        userDetail.setLivingUrl(dto.getLivingImgUrl());
        if (!Strings.isNullOrEmpty(userDetail.getIdcardNo())) {
            userDetail.setIdcardNo(userDetail.getIdcardNo().toUpperCase());
        }
        userDetailService.updateFullUserDetail(user, userDetail);
    }




    @Override
    public Boolean replaceIdCard(IdCardVaildDto idCardVaildDto ,String userId) {
        log.info("更换证件了：" + JSON.toJSONString(idCardVaildDto));
        String idCardEndTime = idCardVaildDto.getIdCardEndTime();
        Date newDate = DateUtil.StrToDate(idCardEndTime, "yyyy-MM-dd");
        if (newDate.before(new Date(System.currentTimeMillis()))) {
            //如果身份在的结束日期小于当前的日期，就认为身份证过期了
            throw new BussException("身份证已过期，请上传有效的身份证件！");
        }
        idCardVaildDto.setUserId(userId);
//        idCardVaildDto.setOrgId(currentUser.getOrgId());
        // 校验现在传的身份证 和之前的身份证 是不是同一个人的
        QueryWrapper<UserDetailEntity> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        UserDetailEntity userDetail = userDetailService.getOne(qw);
        if (userDetail != null) {
            if (!idCardVaildDto.getIdcardName().equals(userDetail.getIdcardName()) || !idCardVaildDto.getIdcardNo().equals(userDetail.getIdcardNo())) {
                throw new BussException("当前提交的信息与原实名信息不一致");
            }
        }
        //在校验认证核验之前，先调鹏元身份证验证的接口
        String msg = "实名认证失败";
        try {
            com.xsungroup.tms.external.vo.ResponseInfo result = pengYuanApi.identity(idCardVaildDto.getIdcardName(), idCardVaildDto.getIdcardNo());
            if (result.getCode() != 1000) {
                msg = result.getMessage();
                throw new BussException(msg);
            }
        } catch (Exception e) {
            throw new BussException(msg);
        }
        //校验失败次数
//        checkNum(currentUser.getUserId());
        try {
            //官网API上给的建议值是80 低于80分的都视为认证不通过
            if (idcardCheck(idCardVaildDto, userId) < 80) {
//                createUserCardCheck(user.getUserId());
                throw new BussException(UserEnum.Code.ID_CARD_CHECK);
            }
        } catch (Exception e) {

        }
        //实名认证校验通过了  现在开始进行业务处理
        //先来c_user_detail表
        if (userDetail != null) {
            userDetail.setIdcardNo(idCardVaildDto.getIdcardNo());
            userDetail.setIdcardName(idCardVaildDto.getIdcardName());
            userDetail.setLivingUrl(idCardVaildDto.getLivingImgUrl());
            userDetail.setIdcardUrl(idCardVaildDto.getIdcardUrl());
            userDetail.setIdcardReUrl(idCardVaildDto.getIdcardReUrl());
            userDetail.setIdCardAuthority(idCardVaildDto.getIdCardAuthority());
            userDetail.setIdCardStartTime(idCardVaildDto.getIdCardStartTime());
            userDetail.setIdCardEndTime(idCardVaildDto.getIdCardEndTime());
            userDetail.setOverdueStatus(0);
            userDetailService.updateById(userDetail);
        }
//        UserInfoEntity userEntity = baseMapper.selectById(userId);
        //实名认证的时候  把身份证上的名字放到t_driver_information上去
        DriverInformationEntity driverInformationEntity = driverInformationService.getOne(new QueryWrapper<DriverInformationEntity>().eq("user_id",userId));
        if(null != driverInformationEntity){
            driverInformationEntity.setIdCardPosUrl(userDetail.getIdcardUrl());
            driverInformationEntity.setIdCardVerUrl(userDetail.getIdcardReUrl());
            driverInformationEntity.setIdCardName(userDetail.getIdcardName());
            driverInformationEntity.setIdCardNo(userDetail.getIdcardNo());
            driverInformationEntity.setLastSubmitTime(LocalDateTime.parse(DateUtil.DateToStr(new Date(),DateUtil.FORMATE_TIME)));
            driverInformationEntity.setAuditMode("1");
            driverInformationService.updateById(driverInformationEntity);
        }
        return true;
    }




    public Double idcardCheck(IdCardVaildDto dto, String userId) {
        com.xsungroup.tms.external.vo.ResponseInfo result = null;
        try {
            result = baiduApi.idcardCheck(dto.getLivingImgUrl(), dto.getIdcardNo(), dto.getIdcardName());
        } catch (Exception e) {
            log.info("人证核验失败，继续下面操作");
//            createUserCardCheck(userId);
        }
        log.info("认证核验返回的结果：" + result.toString());
        Map map = (Map) result.getData();
        if (map == null) {
            throw new BussException("认证失败，请确认身份信息是否正确");
        }
        String data = map.get("score") == null ? "0" : map.get("score").toString();
        return Double.parseDouble(data);
    }


}
