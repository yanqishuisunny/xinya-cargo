package com.cargo.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;
import com.cargo.alipay.model.dto.TradeAppPayDto;
import com.cargo.alipay.model.dto.TradePrecreateDto;
import com.cargo.alipay.model.dto.TransCommonQueryDto;
import com.cargo.alipay.model.dto.TransUniTransferDto;

import java.util.Map;

/**
 * @Auther: xinzs
 * @Date: 2020/10/30 15:27
 */
public interface AliPayService {

    AlipayTradePrecreateResponse tradePrecreate(TradePrecreateDto dto) throws AlipayApiException;

    AlipayTradeAppPayResponse tradeAppPay(TradeAppPayDto dto) throws AlipayApiException;

    AlipayFundTransUniTransferResponse transUniTransfer(TransUniTransferDto dto) throws AlipayApiException;

    void callback(Map<String, String> params, String paramsJson) throws AlipayApiException;

    AlipayFundTransCommonQueryResponse transCommonQuery(TransCommonQueryDto dto);
}
