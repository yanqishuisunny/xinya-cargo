package com.commom.supper;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @Description: 基础Entity封装.
* @Author: kingJing
* @Date: 2019/7/5 13:53
**/
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class BaseEntity extends Convert implements Serializable{
    private static final long serialVersionUID = 1L;


    /**
     * 创建时间
      */
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 是否有效
     */
    @TableLogic
    @TableField(value = "is_able",fill = FieldFill.INSERT)
    private Integer isAble;






}
