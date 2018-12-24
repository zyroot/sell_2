package com.eim.controller;

import com.eim.dto.OrderMasterDto;
import com.eim.service.OrderService;
import com.eim.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by Zy on 2018/12/14.
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public String create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl")String returnUrl, Map map){
        //查询订单
        OrderMasterDto orderMasterDto = orderService.findOne(orderId);

        //发起支付
        PayResponse payResponse = payService.create(orderMasterDto);
        map.put("pay",payResponse);
        map.put("returnUrl",returnUrl);

        return "pay/pay.html";
    }

    /**
     * 微信支付异步回调
     * String notifyData 接收微信传入的 xml
     * @RequestBody:表示接收的是文本格式内容
     */
    @PostMapping("/notify")
    public String notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        //回复微信异步回调，已成功接收。停止回调
        return "pay/paySuccess.html";
    }

}
