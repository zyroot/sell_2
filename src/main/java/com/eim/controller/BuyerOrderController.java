package com.eim.controller;

import com.eim.VO.ResultVO;
import com.eim.convert.OrderForm2OrderMasertDTOConvert;
import com.eim.dto.OrderMasterDto;
import com.eim.enums.ExceptionEnum;
import com.eim.exception.SellException;
import com.eim.form.OrderForm;
import com.eim.service.BuyerService;
import com.eim.service.OrderService;
import com.eim.util.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zy on 2018/12/13.
 */
@Slf4j
@RestController
@RequestMapping(value = "/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】 表单验证（参数错误） orderForm={}",orderForm);
            throw new SellException(ExceptionEnum.ORDER_FORM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderMasterDto orderMasterDto = OrderForm2OrderMasertDTOConvert.convert(orderForm);
        if(CollectionUtils.isEmpty(orderMasterDto.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空 catlist={}",orderMasterDto.getOrderDetailList());
            throw new SellException(ExceptionEnum.CAR_EMPTY);
        }
        OrderMasterDto updateResult = orderService.createOrder(orderMasterDto);

        HashMap<Object, Object> map = new HashMap<>();

        map.put("orderId",updateResult.getOrderId());

        return ResultVoUtil.success(map);
    }

    //订单列表
    @PostMapping("/list")
    public ResultVO<OrderMasterDto> list(@RequestParam(value = "openid")String openid,
                           @RequestParam(value = "page",defaultValue = "0")Integer page,
                           @RequestParam(value = "size",defaultValue = "10")Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("【订单列表】openid为空 ， openid={}",openid);
            throw new SellException(ExceptionEnum.ORDER_FORM_ERROR);
        }
        PageRequest request = new PageRequest(page,size);
        Page<OrderMasterDto> orderPage = orderService.findList(openid, request);
        return ResultVoUtil.success(orderPage.getContent());
    }

    //单个订单详情
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId){

        OrderMasterDto orderMasterDto = buyerService.findOrderOne(openid, orderId);

        return ResultVoUtil.success(orderMasterDto);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId){
        OrderMasterDto orderMasterDto = buyerService.cancelOrderOne(openid, orderId);
        return ResultVoUtil.success();
    }
}
