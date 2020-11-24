package com.cargo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.cache.Constant;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class UserInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value = "user_id")
	private String userId;
    /**
     * 集团ID编号
     */
	private String groupId;
    /**
     * 分公司ID
     */
	private String orgId;
    /**
     * 工号
     */
	private String userCode;
    /**
     * 名称
     */
	private String userName;
    /**
     * 别名
     */
	private String aliasName;
    /**
     * 密码
     */
	private String password;
    /**
     * 部门
     */
	private String departId;
    /**
     *人员角色(1:货主 2:承运商 3:车主 4:平台 5:其他)
     */
    private String userRole;
    /**
     * 数据权限
     */
	private String dataRole;
    /**
     * 菜单权限
     */
	private String menuRole;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型（1普通2企管3超管）
     */
    private Integer userType;

    /**
     * 最近登陆时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 角色说明
     */
	private String remark;
    /**
     * 状态 0:停用  1:启用
     */
	private Integer status;

    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;



    /**
     * app 端注册
     * @param userCode
     * @param orgId
     * @param roleId
     * @param password
     */
    public UserInfoEntity(String userCode, String orgId, String roleId, String password){
        this.userCode = userCode;
        this.groupId = orgId;
        this.phoneNo = userCode;
        this.orgId = orgId;
        this.password = password;
    }

    public UserInfoEntity(String userCode, String password, String phoneNo) {
        this.userCode = userCode;
        this.password = password;
        this.phoneNo = phoneNo;
        this.status = Constant.Status.ENABLE.getValue();
    }


}
