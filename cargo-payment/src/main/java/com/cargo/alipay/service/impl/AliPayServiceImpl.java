package com.cargo.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipayFundTransCommonQueryRequest;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayFundTransCommonQueryResponse;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.cargo.alipay.model.dto.TradeAppPayDto;
import com.cargo.alipay.model.dto.TradePrecreateDto;
import com.cargo.alipay.model.dto.TransCommonQueryDto;
import com.cargo.alipay.model.dto.TransUniTransferDto;
import com.cargo.alipay.model.entity.AlipayNotifyParam;
import com.cargo.alipay.service.AliPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: xinzs
 * @Date: 2020/10/30 15:27
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    protected static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);

    @Value("${alipay.appId}")
    private String APP_ID;

    @Value("${alipay.gateWay}")
    private String GATEWAY;

    @Value("${alipay.app_private_key}")
    private String APP_PRIVATE_KEY;

    @Value("${alipay.alipay_public_key}")
    private String ALIPAY_PUBLICKEY;

    @Value("${alipay.callback.url}")
    private String ALIPAY_CALLBACK_URL;


    /**
     * 当面付
     *
     * @param dto
     * @return
     * @throws AlipayApiException
     */
    @Override
    public AlipayTradePrecreateResponse tradePrecreate(TradePrecreateDto dto) throws AlipayApiException {
        AlipayTradePrecreateResponse response = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLICKEY, "RSA2");

            AlipayTradePrecreateRequest request = getAlipayTradePrecreateRequest(dto);
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功。");
            } else {
                System.out.println("调用失败，原因：" + response.getMsg() + "，" + response.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
//        assertThat(response.isSuccess(), is(true));
//        assertThat(response.getCode(), is("10000"));
        return response;
    }

    public AlipayTradePrecreateRequest getAlipayTradePrecreateRequest(TradePrecreateDto dto) {

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        BeanUtils.copyProperties(dto, model);

//        model.setOutTradeNo(UUID.randomUUID().toString());
        model.setOutTradeNo("NO"+new Date());
        model.setTotalAmount("0.03");
        //商品的标题/交易标题/订单标题/订单关键字等。
        //注意：不可使用特殊字符，如 /，=，& 等。
        model.setSubject("20111815390001_新雅有限公司一二三四五六七");
        request.setBizModel(model);
        request.setNotifyUrl(ALIPAY_CALLBACK_URL);
        return request;

    }


    /**
     * APP支付
     *
     * @param dto
     * @return
     * @throws AlipayApiException
     */
    @Override
    public AlipayTradeAppPayResponse tradeAppPay(TradeAppPayDto dto) throws AlipayApiException {
        AlipayTradeAppPayResponse response = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLICKEY, "RSA2");

            AlipayTradeAppPayRequest request = getAlipayTradeAppPayRequest(dto);

            /** 通过alipayClient调用API，获得对应的response类  **/
            response = alipayClient.sdkExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功。");
            } else {
                System.out.println("调用失败，原因：" + response.getMsg() + "，" + response.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

//        String orderString = response.getBody();
//        //必须拥有正确的签名串
//        Assert.assertThat(orderString, StringContains.containsString("&sign="));

        return response;
    }

    public AlipayTradeAppPayRequest getAlipayTradeAppPayRequest(TradeAppPayDto dto) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        BeanUtils.copyProperties(dto, model);

        //商户网站唯一订单号
        model.setOutTradeNo("NB"+new Date());
        //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000](必选)
        model.setTotalAmount("16.89");
        //商品标题/交易标题/订单标题/订单关键字等。(商品标题(必循))
        //注意：不可使用特殊字符，如 /，=，& 等。
        model.setSubject("这是标题位");
        //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。(商品的具体描述信息(可选))
        model.setBody("我是订单描述(这是上海到北京的石油单)");


        //销售产品码，商家和支付宝签约的产品码(可选)
//        model.setProductCode("QUICK_MSECURITY_PAY");


        //该笔订单允许的最晚付款时间5m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）
        model.setTimeoutExpress("30m");
        request.setBizModel(model);
        request.setNotifyUrl(ALIPAY_CALLBACK_URL);
        return request;

    }

    /**
     * 单笔转账至支付宝接口
     *
     * @param dto
     * @return
     */
    @Override
    public AlipayFundTransUniTransferResponse transUniTransfer(TransUniTransferDto dto) throws AlipayApiException {
        AlipayFundTransUniTransferResponse response = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLICKEY, "RSA2");
//            AlipayClient alipayClient = new DefaultAlipayClient(getClientParams());
            AlipayFundTransUniTransferRequest request = getAlipayFundTransUniTransferRequest(dto);
            response = alipayClient.certificateExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功。");
            } else {
                System.out.println("调用失败，原因：" + response.getMsg() + "，" + response.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

        return response;
    }


    public AlipayFundTransUniTransferRequest getAlipayFundTransUniTransferRequest(TransUniTransferDto dto) {
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        BeanUtils.copyProperties(dto, model);

        Participant participant = new Participant();

        /** 参与方的唯一标识,收款支付宝账号或者支付宝吧账号唯一会员ID **/
        participant.setIdentity("2088******");

        /** 参与方的标识类型：ALIPAY_USER_ID 支付宝的会员ID  **/
        participant.setIdentityType("ALIPAY_USER_ID");

        /** 参与方真实姓名，如果非空，将校验收款支付宝账号姓名一致性。当identity_type=ALIPAY_LOGON_ID时，本字段必填 **/
        participant.setName("张三");

        model.setPayeeInfo(participant);

        /** 业务备注  **/
        model.setRemark("单笔转账");

        /** 商户端的唯一订单号，对于同一笔转账请求，商户需保证该订单号唯一 **/
        model.setOutBizNo("2020062900001");

        /** 转账金额，TRANS_ACCOUNT_NO_PWD产品取值最低0.1  **/
        model.setTransAmount("0.1");

        /** 产品码，单笔无密转账到支付宝账户固定为：TRANS_ACCOUNT_NO_PWD **/
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");

        /** 场景码，单笔无密转账到支付宝账户固定为：DIRECT_TRANSFER  **/
        model.setBizScene("DIRECT_TRANSFER");

        /** 转账业务的标题，用于在支付宝用户的账单里显示 **/
        model.setOrderTitle("转账标题");

        request.setBizModel(model);
        request.setNotifyUrl(ALIPAY_CALLBACK_URL);
        return request;
    }

    @Override
    public void callback(Map<String, String> params, String paramsJson) throws AlipayApiException {

        // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
        this.check(params);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                AlipayNotifyParam param = buildAlipayNotifyParam(params);
                String trade_status = param.getTradeStatus();
                // 支付成功
                if (trade_status.equals("TRADE_SUCCESS")
                        || trade_status.equals("TRADE_FINISHED")) {
                    // 处理支付成功逻辑
                    try {
                        // 处理业务逻辑。。。

                    } catch (Exception e) {
                        logger.error("支付宝回调业务处理报错,params:" + paramsJson, e);
                    }
                } else {
                    logger.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}", trade_status, paramsJson);
                }
            }
        });
    }


    private AlipayNotifyParam buildAlipayNotifyParam(Map<String, String> params) {
        String json = JSON.toJSONString(params);
        return JSON.parseObject(json, AlipayNotifyParam.class);
    }

    /**
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     *
     * @param params
     * @throws AlipayApiException
     */
    private void check(Map<String, String> params) throws AlipayApiException {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        /*Order order = getOrderByOutTradeNo(outTradeNo); //等具体方法
        if (order == null) {
            throw new AlipayApiException("out_trade_no错误");
        }*/

        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        /*long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
        if (total_amount != order.getPayPrice().longValue()) {
            throw new AlipayApiException("error total_amount");
        }*/

        // 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        // 第三步可根据实际情况省略

        // 4、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(APP_ID)) {
            throw new AlipayApiException("app_id不一致");
        }
    }


    /**
     * 查询转账结果
     *
     * @param dto
     * @return
     */
    @Override
    public AlipayFundTransCommonQueryResponse transCommonQuery(TransCommonQueryDto dto) {
        AlipayFundTransCommonQueryResponse response = null;
        try {
            // 1. 创建AlipayClient实例
            AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLICKEY, "RSA2");
//            AlipayClient alipayClient = new DefaultAlipayClient(getClientParams());

            response = alipayClient.certificateExecute(getClientParams(dto));
            if (response.isSuccess()) {
                System.out.println("调用成功。");
            } else {
                System.out.println("调用失败，原因：" + response.getMsg() + "，" + response.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

        return response;
    }

    public AlipayFundTransCommonQueryRequest getClientParams(TransCommonQueryDto dto) {

        /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.fund.trans.common.query(转账业务单据查询接口) **/
        AlipayFundTransCommonQueryRequest request = new AlipayFundTransCommonQueryRequest();

        /** 设置业务参数，具体接口参数传值以文档说明为准：https://opendocs.alipay.com/apis/api_28/alipay.fund.trans.common.query/  **/
        AlipayFundTransCommonQueryModel model = new AlipayFundTransCommonQueryModel();

        BeanUtils.copyProperties(dto, model);

        /** 固定值：TRANS_ACCOUNT_NO_PWD：单笔无密转账到支付宝账户  **/
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");

        /** 支付宝转账单据号,单笔转账接口返回  **/
        model.setOrderId("20200630110070000002120000435728");

        /** 描述特定的业务场景，如果传递了out_biz_no则该字段为必传，固定值：DIRECT_TRANSFER：B2C 现金红包、单笔无密转账 **/
        model.setBizScene("DIRECT_TRANSFER");

        /** 商户转账唯一订单号，单笔转账接口的入参  **/
        model.setOutBizNo("2020063000001");

        request.setBizModel(model);
        return request;
    }


    public CertAlipayRequest getClientParams() {
        CertAlipayRequest certParams = new CertAlipayRequest();
        certParams.setServerUrl(GATEWAY);
        //请更换为您的AppId
        certParams.setAppId(APP_ID);
        //请更换为您的PKCS8格式的应用私钥
        certParams.setPrivateKey(APP_PRIVATE_KEY);
        //请更换为您使用的字符集编码，推荐采用utf-8
        certParams.setCharset("utf-8");
        certParams.setFormat("json");
        certParams.setSignType("RSA2");
        //请更换为您的应用公钥证书文件路径
        certParams.setCertPath("/home/foo/appCertPublicKey_2019091767145019.crt");
        //请更换您的支付宝公钥证书文件路径
        certParams.setAlipayPublicCertPath("/home/foo/alipayCertPublicKey_RSA2.crt");
        //更换为支付宝根证书文件路径
        certParams.setRootCertPath("/home/foo/alipayRootCert.crt");
        return certParams;
    }
}
