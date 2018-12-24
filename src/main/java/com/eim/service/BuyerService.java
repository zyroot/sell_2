package com.eim.service;

import com.eim.dto.OrderMasterDto;

/**
 * 买家
 * Created by Zy on 2018/12/13.
 */
public interface BuyerService {

    /**
     * 查询单个订单
     */
    public OrderMasterDto findOrderOne(String openid,String orderId);

    /**
     * 取消单个订单
     */
    public OrderMasterDto cancelOrderOne(String openid,String orderId);
}
