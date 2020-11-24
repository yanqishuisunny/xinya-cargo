package com.cargo.alipay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayFundTransCommonQueryResponse;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.cargo.alipay.model.dto.TradeAppPayDto;
import com.cargo.alipay.model.dto.TradePrecreateDto;
import com.cargo.alipay.model.dto.TransCommonQueryDto;
import com.cargo.alipay.model.dto.TransUniTransferDto;
import com.cargo.alipay.service.AliPayService;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: xinzs
 * @Date: 2020/10/30 15:26
 */
@Api(tags = "支付宝")
@RestController
@RequestMapping("/api/payment/alipay")
public class AliPayController {

    @Value("${alipay.alipay_public_key}")
    private String ALIPAY_PUBLICKEY;

    @Autowired
    AliPayService aliPayService;

    protected static final Logger logger = LoggerFactory.getLogger(AliPayController.class);

    /**
     * 当面付
     *
     * @param dto
     * @return
     * @throws AlipayApiException
     */
    @ApiOperation(value = "当面付")
    @PostMapping("/tradePrecreate")
    public ResponseInfo tradePrecreate(@RequestBody TradePrecreateDto dto) throws AlipayApiException {

        AlipayTradePrecreateResponse response = aliPayService.tradePrecreate(dto);

        return ResponseUtil.success(response);
    }


    /**
     * APP支付
     *
     * @param dto
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/tradeAppPay")
    public ResponseInfo tradeAppPay(@RequestBody TradeAppPayDto dto) throws AlipayApiException {
        AlipayTradeAppPayResponse response = aliPayService.tradeAppPay(dto);
        return ResponseUtil.success(response);
    }


    /**
     * 单笔转账至支付宝接口
     *
     * @param dto
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/tansUniTransfer")
    public ResponseInfo tansUniTransfer(@RequestBody TransUniTransferDto dto) throws AlipayApiException {
        AlipayFundTransUniTransferResponse response = aliPayService.transUniTransfer(dto);
        return ResponseUtil.success(response);
    }


    /**
     * 回调接口
     *
     * @param request
     * @return
     */
    @PostMapping("/callback")
    public String callback(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = convertRequestParamsToMap(request); // 将异步通知中收到的待验证所有参数都存放到map中
        String paramsJson = JSON.toJSONString(params);
        logger.info("支付宝回调，{}", paramsJson);

        try {
            // 调用SDK验证签名

            //RSA2密钥验签("异步通知", "支付宝公钥数据", charset,sign_type)
            boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLICKEY, "utf-8", "RSA2");
            //公钥证书验签("异步通知", "支付宝公钥证书路径", charset,sign_type);
//        boolean rsaCertCheckV1= AlipaySignature.rsaCertCheckV1(map, "支付宝公钥证书路径", "utf-8","RSA2");

            if (signVerified) {
                logger.info("支付宝回调签名认证成功");

                aliPayService.callback(params, paramsJson);

                // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
                // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
                return "success";
            } else {
                logger.info("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
                return "failure";
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝回调签名认证失败,paramsJson:{},errorMsg:{}", paramsJson, e.getMessage());
            return "failure";
        }
    }


    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }


    /**
     * 查询转账结果
     * @param dto
     * @return
     */
    @PostMapping()
    public ResponseInfo transCommonQuery(@RequestBody TransCommonQueryDto dto){

        AlipayFundTransCommonQueryResponse response = aliPayService.transCommonQuery(dto);

        return ResponseUtil.success(response);
    }

}
