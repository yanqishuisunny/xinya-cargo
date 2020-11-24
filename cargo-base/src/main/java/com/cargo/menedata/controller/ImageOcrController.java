package com.cargo.menedata.controller;


import com.alibaba.fastjson.JSONArray;
import com.cargo.common.ImageOcrEnum;
import com.cargo.menedata.dto.ImageOcrDto;
import com.cargo.menedata.dto.MsgAuthentDto;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import com.google.common.base.Strings;
import com.xsungroup.tms.external.api.BaiduApi;
import com.xsungroup.tms.external.api.CountryApi;
import com.xsungroup.tms.external.api.PengYuanApi;
import com.xsungroup.tms.external.vo.baidu.RoadTransportCertificateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 赵恒亮
 * 2019-11-23 对代码进行优化
 */
@Slf4j
@RestController
@RequestMapping(value = "api/image")
@Api(tags = "21-调第三方接口进行图片识别")
public class ImageOcrController {

    @Autowired
    private BaiduApi baiduApi;

    @Autowired
    private CountryApi countryApi;

    @Autowired
    private PengYuanApi pengYuanApi;

    private static final String name = "道路运输证";
    private static final String NAME_SAFETY = "安全生产许可证";
    private static final String DANGED = "危险化学品经营许可证";


    @ApiOperation(value = "2101-图片OCR识别")
    @RequestMapping(path = "imageOcr", method = RequestMethod.POST)
    public ResponseInfo iamgeOCR(@RequestBody ImageOcrDto imageOcrDto) {
        if (Strings.isNullOrEmpty(imageOcrDto.getType())) {
            return ResponseUtil.error();
        }
        ImageOcrEnum imageOcrEnum = ImageOcrEnum.getImge(imageOcrDto.getType());
        if (imageOcrEnum == null) {
            return ResponseUtil.error();
        }

        //0表示身份证识别，1营业执照识别，2表示驾驶证识别，3行驶证识别,4交通局的车辆信息查询（返回车的好多信息），5企业的道路运输经营许可证，
        //6危险化学品经营许可证,7百度-自定义模板（中华人民共和国道路运输证),8安全生产许可证
        com.xsungroup.tms.external.vo.ResponseInfo responseInfo = null;
        switch (imageOcrEnum) {
            case IDENTITY:
                log.info("身份证调用三方项目的开始时间:"+LocalDateTime.now());
                responseInfo = baiduApi.idcard(imageOcrDto.getImagePath(), imageOcrDto.getIdCardSide());
                break;
            case BUSHINESS:
                log.info("营业执照调用三方项目的开始时间"+ LocalDateTime.now());
                responseInfo = baiduApi.business(imageOcrDto.getImagePath());
                log.info("营业执照调用三方项目的结束时间"+ LocalDateTime.now());
                break;
            case DRIVES:
                log.info("驾驶证调用三方项目的开始时间:"+LocalDateTime.now());
                responseInfo = baiduApi.driver(imageOcrDto.getImagePath());
                break;
            case TRAVEL:
                log.info("行驶证调用三方项目的开始时间:"+LocalDateTime.now());
                responseInfo = baiduApi.travel(imageOcrDto.getImagePath(), imageOcrDto.getIdCardSide());
                break;
            case RTA:
                log.info("交通局车辆信息查询调用三方项目的开始时间:"+LocalDateTime.now());
                responseInfo = countryApi.vehicle(imageOcrDto.getCarNo(), imageOcrDto.getLicenceType());
                break;
            case ROAD:
                log.info("道路运输经营许可证调用三方项目的开始时间:"+LocalDateTime.now());
                responseInfo = baiduApi.custom(imageOcrDto.getImagePath());
                break;
            case DANGER:
                log.info("危险品证调用三方项目的开始时间:"+LocalDateTime.now());
                com.xsungroup.tms.external.vo.ResponseInfo<RoadTransportCertificateResponse> danger = baiduApi.danger(imageOcrDto.getImagePath());

                responseInfo = danger;
                break;
            case CUSTOM:
                log.info("中华人民共和国道路运输证调用三方项目的开始时间:"+LocalDateTime.now());
                com.xsungroup.tms.external.vo.ResponseInfo<RoadTransportCertificateResponse> responseInfoTemp = baiduApi.roadTransportCertificate(imageOcrDto.getImagePath());
                responseInfo = responseInfoTemp;
                break;
            case SAFETY:
                log.info("安全成产许可证调用三方项目的开始时间:"+LocalDateTime.now());
                com.xsungroup.tms.external.vo.ResponseInfo<RoadTransportCertificateResponse> safety = baiduApi.safety(imageOcrDto.getImagePath());
                responseInfo = safety;
                break;
            default:
                return ResponseUtil.error();
        }
        log.info("调用三方项目的结束时间"+ LocalDateTime.now());
        Map<String, Object> toJSON = (Map<String, Object>) JSONArray.toJSON(responseInfo);
        log.info("识别完成");
        return ResponseUtil.success(toJSON.get("data"));

    }

    @ApiOperation(value = "2102-信息检测")
    @RequestMapping(path = "msgCheck", method = RequestMethod.POST)
    public ResponseInfo msgCheck(@RequestBody MsgAuthentDto msgAuthentDto) {
        //0身份信息认证，1企业信息认证，2企业法人股东高管核查,3驾驶证核查
        if (!Strings.isNullOrEmpty(msgAuthentDto.getType())) {
            return ResponseUtil.error();
        }
        ImageOcrEnum imageOcrEnum = ImageOcrEnum.getImge(msgAuthentDto.getType());
        if (imageOcrEnum == null) {
            return ResponseUtil.error();
        }

        com.xsungroup.tms.external.vo.ResponseInfo responseInfo;
        switch (imageOcrEnum) {
            case IDENTITY:
                responseInfo = pengYuanApi.identity(msgAuthentDto.getUserName(), msgAuthentDto.getIdCardNo());
                break;
            case BUSHINESS:
                responseInfo = pengYuanApi.enterprise(msgAuthentDto.getOrgCode(), msgAuthentDto.getCorpName(), msgAuthentDto.getRegisterNo());
                break;
            case DRIVES:
                responseInfo = pengYuanApi.legal(msgAuthentDto.getUserName(), msgAuthentDto.getIdCardNo(), msgAuthentDto.getCorpName(), msgAuthentDto.getCorpId());
                break;
            case TRAVEL:
                responseInfo = pengYuanApi.driver(msgAuthentDto.getUserName(), msgAuthentDto.getIdCardNo());
                break;
            default:
                return ResponseUtil.error();
        }
        Map<String, Object> map = (Map<String, Object>) JSONArray.toJSON(responseInfo);
        return ResponseUtil.success(map.get("data"));

    }

}
