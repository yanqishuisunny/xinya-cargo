package com.cargo.feign.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xinzs
 * @Date: 2020/11/10 13:22
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CarrierdetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 框架协议（存路径）
     */
    @ApiModelProperty("框架协议（存路径）")
    private String agreementUrl;
    /**
     * 审核方式 0:自动  1:人工
     */
    @ApiModelProperty("审核方式 0:自动  1:人工")
    private String auditMode;
    /**
     * 审核人pk
     */
    @ApiModelProperty("审核人pk")
    private String auditUser;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String carrierId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createUser;
    /**
     * 首次审核时间
     */
    @ApiModelProperty("首次审核时间")
    private String firstAuditTime;
    /**
     * 首次提交时间
     */
    @ApiModelProperty("首次提交时间")
    private String firstSubmitTime;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String gmtModified;
    /**
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
    /**
     * 最新审核时间
     */
    @ApiModelProperty("最新审核时间")
    private String lastAuditTime;
    /**
     * 最近提交时间
     */
    @ApiModelProperty("最近提交时间")
    private String lastSubmitTime;
    /**
     * 法人身份证_签发机关
     */
    @ApiModelProperty("法人身份证_签发机关")
    private String legalPersonIdCardIssuedOrg;
    /**
     * 法人身份证图片URL 反面
     */
    @ApiModelProperty("法人身份证图片URL 反面")
    private String legalPersonIdCardReUrl;
    /**
     * 法人身份证图片URL 正面
     */
    @ApiModelProperty("法人身份证图片URL 正面")
    private String legalPersonIdCardUrl;
    /**
     * 法人身份证_结束有效期
     */
    @ApiModelProperty("法人身份证_结束有效期")
    private String legalPersonIdCardValidityEnd;
    /**
     * 法人身份证_开始有效期
     */
    @ApiModelProperty("法人身份证_开始有效期")
    private String legalPersonIdCardValidityStart;
    /**
     * 法人身份证号
     */
    @ApiModelProperty("法人身份证号")
    private String legalPersonIdNo;
    /**
     * 法人姓名
     */
    @ApiModelProperty("法人姓名")
    private String legalPersonName;
    /**
     * 法人身份证过期状态 1.已过期
     */
    @ApiModelProperty("法人身份证过期状态 1.已过期")
    private Integer legalPersonOverdueStatus;
    /**
     * 营业执照-企业地址
     */
    @ApiModelProperty("营业执照-企业地址")
    private String licenseAddress;
    /**
     * 营业执照-资本类型
     */
    @ApiModelProperty("营业执照-资本类型")
    private String licenseCapitalType;
    /**
     * 营业执照-发证机关
     */
    @ApiModelProperty("营业执照-发证机关")
    private String licenseIssuance;
    /**
     * 营业执照-社会统一信用代码
     */
    @ApiModelProperty("营业执照-社会统一信用代码")
    private String licenseOrgCode;
    /**
     * 营业执照-企业名称
     */
    @ApiModelProperty("营业执照-企业名称")
    private String licenseOrgName;
    /**
     * 营业执照-注册资本
     */
    @ApiModelProperty("营业执照-注册资本")
    private String licenseRegisteredCapital;
    /**
     * 营业执照-图片URL
     */
    @ApiModelProperty("营业执照-图片URL")
    private String licenseUrl;
    /**
     * 营业执照-证件过期状态 1，已过期
     */
    @ApiModelProperty("营业执照-证件过期状态 1，已过期")
    private Integer licenseValidOverdueStatus;
    /**
     * 营业执照-有效期结束时间
     */
    @ApiModelProperty("营业执照-有效期结束时间")
    private String licenseValidTimeEnd;
    /**
     * 营业执照-有效期开始时间
     */
    @ApiModelProperty("营业执照-有效期开始时间")
    private String licenseValidTimeStart;
    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String orgContactMan;
    /**
     * 公司联系号码
     */
    @ApiModelProperty("公司联系号码")
    private String orgContactPhone;
    /**
     * 公司邮箱
     */
    @ApiModelProperty("公司邮箱")
    private String orgEmail;
    /**
     * 公司传真
     */
    @ApiModelProperty("公司传真")
    private String orgFax;
    /**
     * 所属组织ID
     */
    @ApiModelProperty("所属组织ID")
    private String orgId;
    /**
     * 所属组织名称
     */
    @ApiModelProperty("所属组织名称")
    private String orgName;
    /**
     * 授权涵访问地址
     */
    @ApiModelProperty("授权涵访问地址")
    private String orgWarrant;
    /**
     * 企业授权书过期状态 1.已过期
     */
    @ApiModelProperty("企业授权书过期状态 1.已过期")
    private Integer orgWarrantOverdueStatus;
    /**
     * 企业授权书-结束日期
     */
    @ApiModelProperty("企业授权书-结束日期")
    private String orgWarrantTimeEnd;
    /**
     * 企业授权书-开始日期
     */
    @ApiModelProperty("企业授权书-开始日期")
    private String orgWarrantTimeStart;
    /**
     * 企业总证件过期状态 1，已过期
     */
    @ApiModelProperty("企业总证件过期状态 1，已过期")
    private Integer overdueStatus;
    /**
     * 道路运输许可证-地址
     */
    @ApiModelProperty("道路运输许可证-地址")
    private String roadTransAddress;
    /**
     * 道路运输许可证-核发机关
     */
    @ApiModelProperty("道路运输许可证-核发机关")
    private String roadTransIssuance;
    /**
     * 道路运输许可证-许可号
     */
    @ApiModelProperty("道路运输许可证-许可号")
    private String roadTransNo;
    /**
     * 道路运输许可证-业户名称
     */
    @ApiModelProperty("道路运输许可证-业户名称")
    private String roadTransOrgName;
    /**
     * 道路运输许可证-许可省
     */
    @ApiModelProperty("道路运输许可证-许可省")
    private String roadTransProvince;
    /**
     * 道路运输许可证-经营范围
     */
    @ApiModelProperty("道路运输许可证-经营范围")
    private String roadTransScope;
    /**
     * 道路运输许可证
     */
    @ApiModelProperty("道路运输许可证")
    private String roadTransUrl;
    /**
     * 道路运输许可证-许可字
     */
    @ApiModelProperty("道路运输许可证-许可字")
    private String roadTransWord;
    /**
     * 道路运输证有效期截至时间
     */
    @ApiModelProperty("道路运输证有效期截至时间")
    private String roadValidTimeEnd;
    /**
     * 道路运输证有效期开始时间
     */
    @ApiModelProperty("道路运输证有效期开始时间")
    private String roadValidTimeStart;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateUser;



    /**
     * 父级ID
     */
    private String parentId;
    /**
     * 集团ID
     */
    private String groupId;
    /**
     * 企业角色 0:承运商 1:货主 3车主
     */
    private Integer orgRole;
    /**
     * 组织类型 0:公司 1:网点
     */
    private Integer orgType;
    /**
     * 来源  0:注册 1:创建
     */
    private Integer orgSource;
    /**
     * 0:企业 1:个人
     */
    private Integer bizType;
    /**
     * 人工审核状态0待审核1拒绝2通过
     */
    private Integer auditStatus;
    /**
     * 接口审核状态0未审核1拒绝2通过
     */
    private Integer extAuditStatus;
    /**
     * 审核备注
     */
    private String checkRemark;

}