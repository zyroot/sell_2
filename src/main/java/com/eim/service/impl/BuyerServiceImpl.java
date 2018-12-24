package com.eim.service.impl;

import com.eim.dataObject.OrderMaster;
import com.eim.dto.OrderMasterDto;
import com.eim.enums.ExceptionEnum;
import com.eim.exception.SellException;
import com.eim.service.BuyerService;
import com.eim.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zy on 2018/12/13.
 */
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderMasterDto findOrderOne(String openid, String orderId) {
        return checkOwe(openid,orderId);
    }

    @Override
    public OrderMasterDto cancelOrderOne(String openid, String orderId) {
        OrderMasterDto orderMasterDto = checkOwe(openid, orderId);
        if(orderMasterDto == null){
            log.error("【取消订单】 查不到该订单 order={}",orderMasterDto);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        OrderMasterDto orderMasterDto1 = orderService.cancleOrder(orderMasterDto);
        return orderMasterDto1;
    }

    private OrderMasterDto checkOwe(String openid, String orderId){
        OrderMasterDto masterDto = orderService.findOne(orderId);
        if(masterDto == null){
            return null;
        }
        if(!masterDto.getBuyerOpenid().equals(openid)){
            log.error("【查询单个订单】 订单所属对象不正确 ,openid={}, r_openid={},w_openid={}",openid,masterDto.getBuyerOpenid(),orderId);
            throw new SellException(ExceptionEnum.ORDER_OWRN_ERROR);
        }
        return masterDto;
    }
}
