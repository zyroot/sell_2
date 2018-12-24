package com.eim.convert;

import com.eim.dataObject.OrderDetail;
import com.eim.dto.OrderMasterDto;
import com.eim.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zy on 2018/12/13.
 */
@Slf4j
public class OrderForm2OrderMasertDTOConvert {

    public static OrderMasterDto convert(OrderForm orderForm){

        OrderMasterDto orderMasterDto = new OrderMasterDto();

        orderMasterDto.setBuyerOpenid(orderForm.getOpenid());
        orderMasterDto.setBuyerPhone(orderForm.getPhone());
        orderMasterDto.setBuyerAddress(orderForm.getAddress());
        orderMasterDto.setBuyerName(orderForm.getName());

        //购物车
        Gson gson = new Gson();
        List<OrderDetail> orderDetail = new ArrayList<>();
        try {
            orderDetail = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            log.error("【json->list<orderdetail>转换错误】 ,json={}",orderForm.getItems());
        }
        orderMasterDto.setOrderDetailList(orderDetail);

        return orderMasterDto;
    }
}
