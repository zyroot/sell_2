package com.eim.controller;

import com.eim.dto.OrderMasterDto;
import com.eim.enums.ExceptionEnum;
import com.eim.exception.SellException;
import com.eim.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.rmi.runtime.Log;

import java.util.Map;

/**
 * 卖家订单
 * Created by Zy on 2018/12/17.
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 当前页（第一页开始）
     * @param size 每页条数
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "size",defaultValue = "10")Integer size,
                       Map map){

        Page<OrderMasterDto> orderDtoPage = orderService.findList(new PageRequest((page - 1), size));
        map.put("orderDtoPage",orderDtoPage);
        map.put("size",size);
        return "seller/list";
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public String cancel(@RequestParam("orderId")String orderId,
                         Map map){
        try {
            OrderMasterDto orderMasterDto = orderService.findOne(orderId);
            orderService.cancleOrder(orderMasterDto);
        } catch (Exception e) {
            log.error("【取消订单】发送异常 Exception={}",e);
            map.put("msg", e.getMessage());
            map.put("url","/seller/order/list");
            return "common/error";
        }
        map.put("msg",ExceptionEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return "common/success";
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam("orderId")String orderId,
                         Map map){
        OrderMasterDto orderMasterDto = null;
        try {
             orderMasterDto = orderService.findOne(orderId);
        } catch (Exception e) {
           log.error("【订单详情】订单不存在 ",e);
           map.put("msg",e.getMessage());
           map.put("url","/seller/order/list");
           return "common/error";
        }
        map.put("orderMasterDto",orderMasterDto);
        return "seller/detail";
    }

    @GetMapping("/finish")
    public String finish(@RequestParam("orderId")String orderId,
                         Map map){
        OrderMasterDto orderMasterDto = null;
        try {
            orderMasterDto = orderService.findOne(orderId);
            orderService.finishOrder(orderMasterDto);
        } catch (Exception e) {
            log.error("【完结订单】订单不存在 ",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return "common/error";
        }
        map.put("msg",ExceptionEnum.ORDER_FINNISH_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return "common/success";
    }
}
