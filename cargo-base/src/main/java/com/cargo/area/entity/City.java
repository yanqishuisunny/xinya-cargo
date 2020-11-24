package com.cargo.area.entity;

import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApiModel
@EqualsAndHashCode(callSuper = true)
@Data
public class City extends TreeNode {

    @ApiModelProperty("区域编码")
    private String code;
    @ApiModelProperty("区域编码")
    private String gbtCode;
    @ApiModelProperty("交通局区域代码")
    private String rtaId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("拼英")
    private String pinyin;
    @ApiModelProperty("拼音简写")
    private String simpleEnglish;
    @ApiModelProperty("名称缩写")
    private String nameAbbreviation;

    public String getPinyin() {
        if (StringUtils.isEmpty(pinyin)){
            return name;
        }
        return pinyin;
    }

    public String getSimpleEnglish() {
        if (StringUtils.isEmpty(simpleEnglish)){
            return name;
        }
        return simpleEnglish;
    }

    public String getNameAbbreviation() {
        if (StringUtils.isEmpty(nameAbbreviation)){
            return name;
        }
        return nameAbbreviation;
    }

    private List<AreaEntity> child;
}
