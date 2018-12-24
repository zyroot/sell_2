package com.eim.service;

import com.eim.dto.OrderMasterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Zy on 2018/12/11.
 */
public interface OrderService {

    /** 创建订单。 */
    OrderMasterDto createOrder(OrderMasterDto orderMasterDto);
    /** 查询单个订单。 */
    OrderMasterDto findOne(String orderId);

    /** 查询订单列表。 */
    Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable);

    /** 查询订单列表。 */
    Page<OrderMasterDto> findList(Pageable pageable);

    /** 取消订单。 */
    OrderMasterDto cancleOrder(OrderMasterDto orderMasterDto);

    /** 完结订单。 */
    OrderMasterDto finishOrder(OrderMasterDto orderMasterDto);

    /** 支付订单。 */
    OrderMasterDto paidOrder(OrderMasterDto orderMasterDto);

}
