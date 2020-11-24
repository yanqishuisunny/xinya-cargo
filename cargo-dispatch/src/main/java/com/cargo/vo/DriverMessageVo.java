package com.cargo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverMessageVo {
    /**
     * 主键
     */
    private String informationId;

    private String cooperationOrgId;

    /**
     * 所属组织ID
     */
    private String orgId;
    /**
     * 所属组织名称
     */
    private String orgName;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 来源（0：注册，1新增）
     */
    private Integer source;
    /**
     * 司机手机号码
     */
    private String phoneNo;
    /**
     * 司机状态 0:空闲  1:出车中  2:请假
     */
    private Integer driverStatus;
    /**
     * 审核状态 0:待实名认证 1:待从业资格认证 2:待审核  3:审核拒绝  4:审核通过
     */
    private Integer auditStatus;
    /**
     * 接口审核状态（0：未审核，1：审核不通过，2：审核通过）
     */
    private Integer extAuditStatus;
    /**
     * 总证件过期状态（1，有过期的证件）
     */
    private Integer overdueStatus;
    /**
     * 是否允许司机自行接单(0：不允许  1：允许)
     */
    private Integer isSelfAcceptOrder;
    /**
     * 身份证姓名
     */
    private String idCardName;
    /**
     * 身份证起始日期
     */
    private String idCardStartTime;
    /**
     * 身份证结束日期
     */
    private String idCardEndTime;
    /**
     * 身份证过期状态（1.已过期）
     */
    private Integer idCardEndTimeOverdueStatus;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 身份证发证机关
     */
    private String idCardAuthority;
    /**
     * 驾驶证姓名
     */
    private String driverLicenseName;
    /**
     * 驾驶证编号
     */
    private String driverLicenseNo;
    /**
     * 准驾车型
     */
    private String driverLicenseLevel;
    /**
     * 驾驶证起始日期
     */
    private String driverLicenseStartTime;
    /**
     * 驾驶证有效期限（年）
     */
    private Integer driverLicenseLimit;
    /**
     * 驾驶证过期状态（1.已过期）
     */
    private Integer driverLicenseOverdueStatus;
    /**
     * 驾驶证发证机关
     */
    private String driverLicenseAuthority;
    /**
     * 人员从业资格证
     */
    private String qualificationUrl;
    /**
     * 从业资格证姓名
     */
    private String qualificationName;
    /**
     * 从业资格证编号
     */
    private String qualificationNo;
    /**
     * 从业资格证有效开始日期
     */
    private String qualificationStartTime;
    /**
     * 从业资格证有效结束日期
     */
    private String qualificationEntTime;
    /**
     * 从业资格证过期状态（1.已过期）
     */
    private String qualificationEntTimeOverdueStatus;
    /**
     * 从业资格证发证机关
     */
    private String qualificationAuthority;
    /**
     * 其他证明
     */
    private String otherCertificate;
    /**
     * 首次提交时间
     */
    private LocalDateTime firstSubmitTime;
    /**
     * 最近提交时间
     */
    private LocalDateTime lastSubmitTime;
    /**
     * 初次审核时间
     */
    private LocalDateTime firstAuditTime;
    /**
     * 最新审核时间
     */
    private LocalDateTime lastAuditTime;
    /**
     * 审核方式（0：人工，1：自动）
     */
    private Integer auditMode;
    /**
     * 审核失败原因
     */
    private String auditFailReason;
    /**
     * 创建人
     */
    private String auditUserName;
    /**
     * 是否绑定（0：未绑定，1：已绑定）
     */
    private Integer isBound;
    /**
     * 解绑原因
     */
    private String unbindReason;
    /**
     * 驾驶证发证机关
     */
    private String createUser;
    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改人姓名
     */
    private String updateUserName;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 是否发放奖励（0：未发，1：已发）
     */
    private Integer isReward;

}
