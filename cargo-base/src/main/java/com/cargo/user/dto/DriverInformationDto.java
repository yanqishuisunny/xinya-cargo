package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29 11:10:25
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DriverInformationDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("是否是APP过来的，1是。0不是")
    private String isApp;

    /**
    * 审核失败原因
    */
    @ApiModelProperty("审核失败原因")
    private String auditFailReason;

    
    /**
    * 审核方式（0：人工，1：自动）
    */
    @ApiModelProperty("审核方式（0：人工，1：自动）")
    private Integer auditMode;

    
    /**
    * 审核状态 0:待实名认证 1:待从业资格认证 2:待审核  3:审核拒绝  4:审核通过
    */
    @ApiModelProperty("审核状态 0:待实名认证 1:待从业资格认证 2:待审核  3:审核拒绝  4:审核通过")
    private Integer auditStatus;

    
    /**
    * 创建人
    */
    @ApiModelProperty("创建人")
    private String auditUserName;

    
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private String createTime;

    
    /**
    * 驾驶证发证机关
    */
    @ApiModelProperty("驾驶证发证机关")
    private String createUser;

    
    /**
    * 创建人姓名
    */
    @ApiModelProperty("创建人姓名")
    private String createUserName;

    
    /**
    * 驾驶证发证机关
    */
    @ApiModelProperty("驾驶证发证机关")
    private String driverLicenseAuthority;

    
    /**
    * 准驾车型
    */
    @ApiModelProperty("准驾车型")
    private String driverLicenseLevel;

    
    /**
    * 驾驶证有效期限（年）
    */
    @ApiModelProperty("驾驶证有效期限（年）")
    private Integer driverLicenseLimit;

    
    /**
    * 驾驶证姓名
    */
    @ApiModelProperty("驾驶证姓名")
    private String driverLicenseName;

    
    /**
    * 驾驶证编号
    */
    @ApiModelProperty("驾驶证编号")
    private String driverLicenseNo;

    
    /**
    * 驾驶证过期状态（1.已过期）
    */
    @ApiModelProperty("驾驶证过期状态（1.已过期）")
    private Integer driverLicenseOverdueStatus;

    
    /**
    * 驾驶证起始日期
    */
    @ApiModelProperty("驾驶证起始日期")
    private String driverLicenseStartTime;

    
    /**
    * 司机状态 0:空闲  1:出车中  2:请假
    */
    @ApiModelProperty("司机状态 0:空闲  1:出车中  2:请假")
    private Integer driverStatus;

    
    /**
    * 接口审核状态（0：未审核，1：审核不通过，2：审核通过）
    */
    @ApiModelProperty("接口审核状态（0：未审核，1：审核不通过，2：审核通过）")
    private Integer extAuditStatus;

    
    /**
    * 初次审核时间
    */
    @ApiModelProperty("初次审核时间")
    private String firstAuditTime;

    
    /**
    * 首次提交时间
    */
    @ApiModelProperty("首次提交时间")
    private String firstSubmitTime;

    
    /**
    * 身份证发证机关
    */
    @ApiModelProperty("身份证发证机关")
    private String idCardAuthority;

    
    /**
    * 身份证结束日期
    */
    @ApiModelProperty("身份证结束日期")
    private String idCardEndTime;

    
    /**
    * 身份证过期状态（1.已过期）
    */
    @ApiModelProperty("身份证过期状态（1.已过期）")
    private Integer idCardEndTimeOverdueStatus;

    
    /**
    * 身份证姓名
    */
    @ApiModelProperty("身份证姓名")
    private String idCardName;

    
    /**
    * 身份证号码
    */
    @ApiModelProperty("身份证号码")
    private String idCardNo;

    
    /**
    * 身份证起始日期
    */
    @ApiModelProperty("身份证起始日期")
    private String idCardStartTime;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String informationId;

    
    /**
    * 是否绑定（0：未绑定，1：已绑定）
    */
    @ApiModelProperty("是否绑定（0：未绑定，1：已绑定）")
    private Integer isBound;

    
    /**
    * 是否发放奖励（0：未发，1：已发）
    */
    @ApiModelProperty("是否发放奖励（0：未发，1：已发）")
    private Integer isReward;

    
    /**
    * 是否允许司机自行接单(0：不允许  1：允许)
    */
    @ApiModelProperty("是否允许司机自行接单(0：不允许  1：允许)")
    private Integer isSelfAcceptOrder;

    
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
    * 其他证明
    */
    @ApiModelProperty("其他证明")
    private String otherCertificate;


//    /**
//     * 总证件过期状态（1，有过期的证件）
//     */
//    @ApiModelProperty("总证件过期状态（1，有过期的证件）")
//    private Integer overdueStatus;

    
    /**
    * 司机手机号码
    */
    @ApiModelProperty("司机手机号码")
    private String phoneNo;

    
    /**
    * 从业资格证发证机关
    */
    @ApiModelProperty("从业资格证发证机关")
    private String qualificationAuthority;

    
    /**
    * 从业资格证有效结束日期
    */
    @ApiModelProperty("从业资格证有效结束日期")
    private String qualificationEntTime;

    
    /**
    * 从业资格证过期状态（1.已过期）
    */
    @ApiModelProperty("从业资格证过期状态（1.已过期）")
    private String qualificationEntTimeOverdueStatus;

    
    /**
    * 从业资格证姓名
    */
    @ApiModelProperty("从业资格证姓名")
    private String qualificationName;

    
    /**
    * 从业资格证编号
    */
    @ApiModelProperty("从业资格证编号")
    private String qualificationNo;

    
    /**
    * 从业资格证有效开始日期
    */
    @ApiModelProperty("从业资格证有效开始日期")
    private String qualificationStartTime;

    
    /**
    * 人员从业资格证
    */
    @ApiModelProperty("人员从业资格证")
    private String qualificationUrl;


    /**
     * 身份证正面
     */
    @ApiModelProperty("身份证正面")
    private String idCardPosUrl;

    /**
     * 身份证反面
     */
    @ApiModelProperty("身份证反面")
    private String idCardVerUrl;

    /**
     * 驾驶证照片
     */
    @ApiModelProperty("驾驶证照片")
    private String driverLicenseUrl;


    /**
     * 驾驶证反面
     */
    @ApiModelProperty("驾驶证反面")
    private String driverLicenseVerUrl;

    
    /**
    * 来源（0：注册，1新增）
    */
    @ApiModelProperty("来源（0：注册，1新增）")
    private Integer source;

    
    /**
    * 解绑原因
    */
    @ApiModelProperty("解绑原因")
    private String unbindReason;

    
    /**
    * 更新人
    */
    @ApiModelProperty("更新人")
    private String updateUser;

    
    /**
    * 修改人姓名
    */
    @ApiModelProperty("修改人姓名")
    private String updateUserName;

    
    /**
    * 用户ID
    */
    @ApiModelProperty("用户ID")
    private String userId;

    /**
     * 搜索状态条件
     */
    private List<Integer> listAuditStatus;
    /**
     * 审核备注
     */
    private String checkRemark;
}