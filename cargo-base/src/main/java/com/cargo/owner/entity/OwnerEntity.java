package com.cargo.owner.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 货主
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_owner")
public class OwnerEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.UUID)
    private String ownerId;


    /**
     * 所属组织ID
     */
    private String orgId;
    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 企业总证件过期状态 1，已过期
     */
    private Integer overdueStatus;

    /**
     * 营业执照-图片URL
     */
    private String licenseUrl;

    /**
     * 营业执照-发证机关
     */
    private String licenseIssuance;

    /**
     * 营业执照-社会统一信用代码
     */
    private String licenseOrgCode;

    /**
     * 营业执照-企业名称
     */
    private String licenseOrgName;

    /**
     * 营业执照-注册资本
     */
    private String licenseRegisteredCapital;

    /**
     * 营业执照-资本类型
     */
    private String licenseCapitalType;

    /**
     * 营业执照-企业地址
     */
    private String licenseAddress;

    /**
     * 营业执照-有效期开始时间
     */
    private String licenseValidTimeStart;

    /**
     * 营业执照-有效期结束时间
     */
    private String licenseValidTimeEnd;

    /**
     * 营业执照-证件过期状态 1，已过期
     */
    private Integer licenseValidOverdueStatus;

    /**
     * 法人身份证图片URL 正面
     */
    private String legalPersonIdCardUrl;

    /**
     * 法人身份证图片URL 反面
     */
    private String legalPersonIdCardReUrl;

    /**
     * 法人身份证_开始有效期
     */
    private LocalDate legalPersonIdCardValidityStart;

    /**
     * 法人身份证_结束有效期
     */
    private LocalDate legalPersonIdCardValidityEnd;

    /**
     * 法人身份证过期状态 1.已过期
     */
    private Integer legalPersonOverdueStatus;

    /**
     * 法人身份证_签发机关
     */
    private String legalPersonIdCardIssuedOrg;

    /**
     * 法人身份证号
     */
    private String legalPersonIdNo;

    /**
     * 法人姓名
     */
    private String legalPersonName;

    /**
     * 授权涵访问地址
     */
    private String orgWarrant;

    /**
     * 企业授权书-开始日期
     */
    private String orgWarrantTimeStart;

    /**
     * 企业授权书-结束日期
     */
    private String orgWarrantTimeEnd;

    /**
     * 企业授权书过期状态 1.已过期
     */
    private Integer orgWarrantOverdueStatus;

    /**
     * 联系人
     */
    private String orgContactMan;

    /**
     * 公司联系号码
     */
    private String orgContactPhone;

    /**
     * 公司邮箱
     */
    private String orgEmail;

    /**
     * 公司传真
     */
    private String orgFax;

    /**
     * 框架协议（存路径）
     */
    private String agreementUrl;

    /**
     * 首次提交时间
     */
    private String firstSubmitTime;

    /**
     * 最近提交时间
     */
    private String lastSubmitTime;

    /**
     * 首次审核时间
     */
    private String firstAuditTime;

    /**
     * 最新审核时间
     */
    private String lastAuditTime;

    /**
     * 审核方式 0:自动  1:人工
     */
    private String auditMode;

    /**
     * 审核人pk
     */
    private String auditUser;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 人工审核状态 0待审核 1拒绝 2通过
     * */
    private Integer auditStatus;

    /**
     * 审核备注
     * */
    private String checkRemark;
}
