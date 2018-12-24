package com.eim.service.impl;

import com.eim.dto.OrderMasterDto;
import com.eim.enums.ExceptionEnum;
import com.eim.exception.SellException;
import com.eim.service.OrderService;
import com.eim.service.PayService;
import com.eim.util.JsonFomateUtil;
import com.eim.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付
 * Created by Zy on 2018/12/14.
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private static final String ORDERNAME = "微信点餐订单";

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderMasterDto orderMasterDto) {
        //配置支付详情
        // PayRequest
        PayRequest payRequest = new PayRequest();
        //openid
        payRequest.setOpenid(orderMasterDto.getBuyerOpenid());
        //订单id
        payRequest.setOrderId(orderMasterDto.getOrderId());
        //订单总金额
        payRequest.setOrderAmount(orderMasterDto.getOrderAmount().doubleValue());
        //订单名字
        payRequest.setOrderName(ORDERNAME);
        //支付类型
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 payRequest={}", JsonFomateUtil.toJson(payRequest));
        //发起支付
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 payResponse={}",JsonFomateUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名（bestPay已做）
        //2.验证支付状态（bestPay已做）
        //3、验证金额是否相等（自己校验）
        //4、验证下单和支付人是否一致（根据具体业务需求而定）
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】 微信异步通知，payResponse={}",payResponse);

        OrderMasterDto masterDto = orderService.findOne(payResponse.getOrderId());
        if(masterDto == null){
            log.error("【微信支付】 微信异步通知，订单不存在 order ={}",masterDto);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        /**
         * 比较金额是否一致
         *   特别考虑：
         *      1、数据类型是否一致
         *      2、数据类型不一致转换时，会发生精度问题
         *      3、0.1 和0.10 也应该是一致的
         */
        if(MathUtil.isMath(masterDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            //修改订单支付状态
            orderService.paidOrder(masterDto);
        }else{
            log.error("【微信支付】 订单总金额不一致 newM={},oldM={}",payResponse.getOrderAmount(),masterDto.getOrderAmount().doubleValue());
            throw new SellException(ExceptionEnum.ORDER_AMOUNT_ERROR);
        }

        return payResponse;
    }

    /**
     * 微信退款
     */
    public RefundResponse reFund(OrderMasterDto orderMasterDto){
        RefundRequest refundRequest = new RefundRequest();
        //微信退款类型
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        //微信退款订单号
        refundRequest.setOrderId(orderMasterDto.getOrderId());
        //微信退款总金额
        refundRequest.setOrderAmount(orderMasterDto.getOrderAmount().doubleValue());
        log.info("【微信退款】 request={}",JsonFomateUtil.toJson(refundRequest));

        //bestPayService调起退款
        RefundResponse refund = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}",JsonFomateUtil.toJson(refund));

        return refund;
    }
}
