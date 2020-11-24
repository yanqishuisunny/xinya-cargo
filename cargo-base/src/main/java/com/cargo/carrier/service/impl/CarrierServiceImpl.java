package com.cargo.carrier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.carrier.dto.CarrierDto;
import com.cargo.carrier.entity.CarrierEntity;
import com.cargo.carrier.mapper.CarrierMapper;
import com.cargo.carrier.service.CarrierService;
import com.cargo.carrier.vo.CarrierVo;
import com.cargo.carrier.vo.CarrierdetailVo;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.OrgUserAssociationEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.OrgMapper;
import com.cargo.user.mapper.OrgUserAssociationMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 承运商信息表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-05
 */
@Service
public class CarrierServiceImpl extends ServiceImpl<CarrierMapper, CarrierEntity> implements CarrierService {

    @Autowired
    OrgMapper orgMapper;
    @Autowired
    CarrierMapper carrierMapper;
    @Autowired
    OrgUserAssociationMapper orgUserAssociationMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addCarrier(CarrierDto dto) {
        String userId = ShiroUtil.currentUserId();

        //1、参数校验
        //1.1、企业是否已存在
        String licenseOrgCode = dto.getLicenseOrgCode();

        CarrierEntity carrierEntity = new CarrierEntity();
        BeanUtils.copyProperties(dto,carrierEntity);

        QueryWrapper<CarrierEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("license_org_code",licenseOrgCode);
        boolean exist = retBool(carrierMapper.selectCount(wrapper));
        if(exist){
            throw new BussException("该企业已存在");
        }



        //1.2、证件是否有效


        //2、存入企业表
        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setOrgName(dto.getLicenseOrgName());
        orgEntity.setOrgRole(0);
        orgEntity.setOrgType(0);
        orgEntity.setPhoneNo(dto.getOrgContactPhone());
        orgEntity.setBizType(0);
        orgEntity.setUserId(ShiroUtil.getUserId());
        orgMapper.insert(orgEntity);

        //3、存入承运商表
        carrierEntity.setOrgId(orgEntity.getOrgId());
        carrierMapper.insert(carrierEntity);


        //4、企业用户关联表新增一条记录
        OrgUserAssociationEntity orgUserAssociationEntity = new OrgUserAssociationEntity();
        orgUserAssociationEntity.setOrgId(orgEntity.getOrgId());
        orgUserAssociationEntity.setOrgRole("0");
        orgUserAssociationEntity.setUserId(userId);

        orgUserAssociationMapper.insert(orgUserAssociationEntity);


        //5、更新user表
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(userId);
        userInfoEntity.setUserRole("2");
        userInfoEntity.setUserType(3);
        userInfoMapper.updateById(userInfoEntity);

        return orgEntity.getOrgId();

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCarrier(CarrierDto dto) {

        String userId = ShiroUtil.currentUserId();

        //1、参数校验
        String licenseOrgCode = dto.getLicenseOrgCode();

        CarrierEntity carrierEntity = new CarrierEntity();
        BeanUtils.copyProperties(dto,carrierEntity);


        //2、更新企业表
        String orgId = dto.getOrgId();
        OrgEntity orgEntity = orgMapper.selectById(orgId);
        orgEntity.setOrgName(dto.getLicenseOrgName());
//        orgEntity.setOrgName(dto.getOrgName());
        orgMapper.updateById(orgEntity);

        //3、更新承运商表
        carrierMapper.updateById(carrierEntity);


    }

    @Override
    public CarrierdetailVo selectCarrierById(String id) {
        OrgEntity orgEntity = orgMapper.selectById(id);

        QueryWrapper<CarrierEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",id);
        CarrierEntity carrierEntity = carrierMapper.selectOne(wrapper);

        CarrierdetailVo carrierdetailVo = new CarrierdetailVo();
        BeanUtils.copyProperties(orgEntity,carrierdetailVo,getNullPropertyNames(orgEntity));
        BeanUtils.copyProperties(carrierEntity,carrierdetailVo,getNullPropertyNames(carrierEntity));
        return carrierdetailVo;
    }

    @Override
    public CarrierdetailVo selectCarrierByRoleId(String id) {
        CarrierEntity carrierEntity = carrierMapper.selectById(id);

        CarrierdetailVo convert = BeanConverter.convert(CarrierdetailVo.class, carrierEntity);

        return convert;
    }


    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
