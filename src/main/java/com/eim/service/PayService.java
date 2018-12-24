package com.eim.service;


import com.eim.dto.OrderMasterDto;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * Created by Zy on 2018/12/14.
 */
public interface PayService {

    PayResponse create(OrderMasterDto orderMasterDto);

    PayResponse notify(String notifyData);

    RefundResponse reFund(OrderMasterDto orderMasterDto);
}
