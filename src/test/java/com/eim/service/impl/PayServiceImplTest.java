package com.eim.service.impl;

import com.eim.dto.OrderMasterDto;
import com.eim.service.OrderService;
import com.eim.service.PayService;
import jdk.nashorn.internal.runtime.ECMAException;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderMasterDto one = orderService.findOne("1542276188031104932");
//        OrderMasterDto orderMasterDto = payService.create(one);
    }

    @Test
    public void refund() throws Exception {
        OrderMasterDto orderMasterDto = orderService.findOne("1545030408915121851");
        OrderMasterDto orderMasterDto1 = orderService.cancleOrder(orderMasterDto);
        System.out.println(orderMasterDto1.toString());
    }

}