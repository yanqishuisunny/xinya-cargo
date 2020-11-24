package com.cargo.area.entity;

import com.alibaba.druid.util.StringUtils;
import com.cargo.utils.EnglishAndChineseUtil;
import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel
@EqualsAndHashCode(callSuper = true)
@Data
public class Province extends TreeNode {
    @ApiModelProperty("区域编码")
    private String code;
    @ApiModelProperty("区域编码")
    private String gbtCode;
    @ApiModelProperty("交通局区域代码")
    private String rtaId;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("省份首字母")
    private String initial;

    @ApiModelProperty("名称缩写")
    private String nameAbbreviation;


    private List<AreaEntity> child;

    public String getInitial() {
        if (StringUtils.isEmpty(initial)){
            return this.name= EnglishAndChineseUtil.getHanziToSimpleName(this.name).substring(0,1);
        }
        return initial;
    }
}
