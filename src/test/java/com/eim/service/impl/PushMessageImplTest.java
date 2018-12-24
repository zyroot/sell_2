package com.eim.service.impl;

import com.eim.dto.OrderMasterDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

    @Autowired
    private PushMessageImpl pushMessage;

    @Test
    public void pushMessage() throws Exception {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setOrderId("1542266523188203943");
        pushMessage.pushMessage(orderMasterDto);

    }

}