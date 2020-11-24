package com.cargo.area.entity;

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
 * 数据字典表
 * </p>
 *
 * @author 何立辉
 * @since 2019-09-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sysdic")
public class SysdicEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	@TableId(value = "id",type = IdType.UUID)
	private String id;

	@ApiModelProperty("父级ID")
	private String parentId;
    /**
     * 是否默认[0：否,1:是]
     */
	@ApiModelProperty("是否默认")
	private Boolean flgDefault;
    /**
     * 字典编码
     */
	@ApiModelProperty("字典编码")
	private String dicCode;
    /**
     * 字典名
     */
	@ApiModelProperty("字典名")
	private String dicName;
    /**
     * 字典值
     */
	@ApiModelProperty("字典值")
	private String dicValue;
    /**
     * 排序号
     */
	@ApiModelProperty("排序号")
	private Integer orderNum;
    /**
     * 状态[0：禁用,1:启用]
     */
	@ApiModelProperty("状态")
	private Integer status;


}
