package com.eim.service.impl;

import com.eim.dataObject.OrderDetail;
import com.eim.dto.OrderMasterDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/12.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String OPENID = "789789";

    private final String ORDERID = "1544596575052693850";

    @Test
    public void createOrder() throws Exception {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setBuyerName("康熙");
        orderMasterDto.setBuyerAddress("成都高升桥");
        orderMasterDto.setBuyerPhone("18280176992");
        orderMasterDto.setBuyerOpenid(OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1235556");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        orderMasterDto.setOrderDetailList(orderDetailList);

        OrderMasterDto order = orderService.createOrder(orderMasterDto);
        log.info("【测试】order={}"+order);
    }

    @Test
    public void findOne() throws Exception {
        OrderMasterDto one = orderService.findOne(ORDERID);
        Assert.assertNotNull(one);
    }

    @Test
    public void findList() throws Exception {

        Page<OrderMasterDto> list = orderService.findList(OPENID, new PageRequest(0, 2));

        Assert.assertNotEquals(0,list.getTotalElements());
    }

    @Test
    public void cancleOrder() throws Exception {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setBuyerOpenid(OPENID);
        orderMasterDto.setOrderId(ORDERID);
        OrderMasterDto orderMasterDto1 = orderService.cancleOrder(orderMasterDto);

        Assert.assertNotNull(orderMasterDto1);
    }

    @Test
    public void finishOrder() throws Exception {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setOrderId(ORDERID);
        OrderMasterDto orderMasterDto1 = orderService.finishOrder(orderMasterDto);
        Assert.assertNotNull(orderMasterDto1);
    }

    @Test
    public void paidOrder() throws Exception {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setOrderId(ORDERID);
        OrderMasterDto orderMasterDto1 = orderService.paidOrder(orderMasterDto);
        Assert.assertNotNull(orderMasterDto1);
    }

    @Test
    public void findlist(){
        PageRequest request = new PageRequest(0, 3);
        Page<OrderMasterDto> list = orderService.findList(request);
        Assert.assertNotEquals(0,list.getTotalElements());
    }
}