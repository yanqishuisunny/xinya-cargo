package com.cargo.area.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cargo.area.dto.AreaDto;
import com.cargo.area.entity.AreaEntity;
import com.cargo.area.entity.City;
import com.cargo.area.service.AreaService;
import com.cargo.area.vo.AreaVo;
import com.cargo.area.vo.CityVo;
import com.cargo.area.vo.ProvinceVo;
import com.cargo.area.vo.SysDicVo;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.supper.SuperController;
import com.commom.utils.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 何立辉
 * @since 2019-07-25
 */
@RestController
@Api(tags = "省市区列表")
@RequestMapping("/api/base/area")
@Slf4j
public class AreaController extends SuperController {
    @Autowired
    private AreaService areaService;


    /**
     * 获取行政区域树
     *
     * @return
     * @throws BussException
     */
    @ApiOperation(value = "获取行政区域树")
    @ApiImplicitParam(name = "name", value = "区域名称/代码", paramType = "query")
    @GetMapping("/tree")
    public ResponseInfo tree(@RequestParam(required = false)
                                     String name) throws BussException {
        QueryWrapper<AreaEntity> qw = new QueryWrapper<>();
        qw.in("area_level", 4, 5, 6);
        if (StringUtils.isNotBlank(name)) {
            qw.and(wrapper -> wrapper.like("code", name).or().like("name", name));
        }
        List<AreaEntity> list = areaService.list(qw);
        if (!CollectionUtils.isEmpty(list)) {
            List<AreaVo> listVo = BeanConverter.convert(AreaVo.class, list);
            return ResponseUtil.success(listVo.stream()
                    .filter(n -> Objects.equals("0", n.getParentId()))
                    .map(e -> TreeUtils.findChildren(e, listVo))
                    .collect(Collectors.toList()));
        }
        return ResponseUtil.success();
    }


    @GetMapping("/getAreaFromRedis")
    public ResponseInfo getAreaFromRedis() throws BussException {
        return ResponseUtil.success(RedisUtil.get("saveOrEdit:area"));
    }





    /**
     * 获取行政区域树- 最后节点
     *
     * @return
     * @throws BussException
     */
    @ApiOperation(value = "获取行政区域树- 最后节点")
    @ApiImplicitParam(name = "parentId", value = "父节点ID", paramType = "query")
    @GetMapping("/lastNodes")
    public ResponseInfo lastNodes(@RequestParam String parentId) throws BussException {
        QueryWrapper<AreaEntity> qw = new QueryWrapper<>();
        qw.eq("parent_id", parentId);
        List<AreaEntity> list = areaService.list(qw);
        List<AreaVo> listVo = BeanConverter.convert(AreaVo.class, list);
        if (listVo == null || listVo.size() == 0) {
            listVo = new ArrayList<>();
            AreaVo areaVo = new AreaVo();
            areaVo.setId(parentId + "00");
            areaVo.setName("无");
            listVo.add(areaVo);
        }
        return ResponseUtil.success(listVo);
    }

    /**
     * 分页demo
     *
     * @return
     * @throws BussException
     */
    @ApiOperation(value = "分页demo")
    @GetMapping("/page")
    public ResponseInfo page(AreaDto dto) {
        QueryWrapper<AreaEntity> qw = new QueryWrapper<>();
        qw.likeRight("name", TypeUtils.castToString(dto.getName(), ""));
        IPage<AreaEntity> iPage = this.getPage(false);
        iPage = areaService.page(iPage, qw);
        return ResponseUtil.success(iPage);
    }

    @ApiOperation(value = "分页demo")
    @GetMapping("/pageMore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "编码", paramType = "query"),
            @ApiImplicitParam(name = "level", value = "等级", paramType = "query")
    })
    public ResponseInfo pageMore(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "code", required = false) String code,
                                 @RequestParam(value = "level", required = false, defaultValue = "4") int level) {
        log.info(name + "||" + code + "||" + level);
        return ResponseUtil.success();
    }


    /**
     * 新增方法.
     *
     * @param t
     */
    @ApiOperation(value = "新增")
    @PostMapping("/create")
    public ResponseInfo create(@RequestBody @Validated(AreaDto.Create.class) AreaDto t) {
        AreaEntity entity = BeanConverter.convert(AreaEntity.class, t);
        areaService.saveArea(entity);
        return ResponseUtil.result("成功");
    }


    /**
     * 修改方法.
     *
     * @param t
     */
    @ApiOperation(value = "修改")
    @PutMapping("/{id}")
    public ResponseInfo updateData(@ApiParam("行政区域ID") @PathVariable("id") String id, @RequestBody @Validated(AreaDto.Update.class) AreaDto t) {
        AreaEntity entity = BeanConverter.convert(AreaEntity.class, t);
        entity.setId(id);
        areaService.editArea(entity);
        return ResponseUtil.result("成功");
    }

    /**
     * 删除方法
     *
     * @param id
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id:.+}")
    public ResponseInfo delete(@ApiParam("行政区域ID") @PathVariable("id") String id) throws BussException {
        AreaEntity entity = areaService.getById(id);
        Assert.notNull(entity, "无效的ID");
        boolean result = areaService.removeById(id);
        return ResponseUtil.result(result);
    }


    /**
     * 级联查询省市
     *
     * @param
     */
    @ApiOperation(value = "级联查询省市")
    @GetMapping("/cascadeQuery")
    public ResponseInfo cascadeQuery(@ApiParam("区域等级") @RequestParam String areaLevel, @ApiParam("区域父ID") @RequestParam String parentId) throws BussException {
        return ResponseUtil.success(areaService.cascadeQuery(areaLevel, parentId));
    }

    @ApiOperation(value = "省份列表")
    @GetMapping("/provinceList")
    public ResponseInfo<ProvinceVo> provinceList() {
        return ResponseUtil.success(areaService.selectProvince());
    }

    @ApiOperation(value = "城市查询")
    @GetMapping("/cityList")
    public ResponseInfo<CityVo> cityList() {
        return ResponseUtil.success(areaService.selectCity());
    }

    @ApiOperation(value = "按照城市名称查询")
    @GetMapping("/cityListByCityName")
    public ResponseInfo<List<City>> cityListByCityName() {
        return ResponseUtil.success(areaService.selectByCityName());
    }

    @ApiOperation(value = "查询热门城市")
    @GetMapping("/queryHeatCity")
    public ResponseInfo<List<SysDicVo>> queryHeatCity() {
        return ResponseUtil.success(areaService.selectHeatCity());
    }



}
