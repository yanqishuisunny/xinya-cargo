package com.commom.supper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.commom.exception.BussException;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 基础控制层封装
 * @Author: kingJing
 * @Date: 2019/7/5 13:38
 **/
@Api
@Slf4j
public class BaseController<T> {

    @Autowired
    private BaseDao<T> baseDao;

    @Autowired
    protected HttpServletRequest request;


    /**
     * 通用新增方法.
     *
     * @param t
     */
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public ResponseInfo saveData(@RequestBody T t) {
        int result = baseDao.insert(t);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }


    /**
     * 通用修改方法.
     *
     * @param t
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public ResponseInfo updateData(@RequestBody T t) {
        int result = baseDao.updateById(t);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }


    /**
     * 单个查询方法.
     *
     * @param map
     */
    @ApiOperation(value = "单个查询")
    @PostMapping("/getById")
    public ResponseInfo selectById(@RequestBody Map<String, Object> map) {
        //通过每个表的主键字段查询，因为每个表的主键字段不同，做如下处理
        Set<String> set = map.keySet();
        //因为参数map里面只会有一个主键，所以直接取set中的第一个元素即可
        //这里先把驼峰转为下划线
        String colume = underscoreName(set.iterator().next());
        return ResponseUtil.success(baseDao.selectOne(new QueryWrapper<T>().eq(colume, map.get(set.iterator().next()).toString())));
    }

    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }


    /**
     * 通用删除方法（物理删除）
     *
     * @param id
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping("/delete/{id}")
    public ResponseInfo deleteData(@PathVariable String id) throws BussException {
        if (StringUtils.isBlank(id)) {
            throw new BussException("请先选择单据");
        }
        baseDao.deleteById(id);
        return ResponseUtil.success();
    }


    /**
     * 批量删除方法（物理删除）
     *
     * @param list
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/deleteBatchIds")
    public ResponseInfo deleteBatchIds(@ApiParam("Id列表") @RequestBody List<String> list) throws BussException {
        if (list == null || list.size() == 0) {
            throw new BussException("请先选择单据");
        }
        int result = baseDao.deleteBatchIds(list);
        return ResponseUtil.success(result);
    }



}
