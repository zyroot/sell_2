package com.eim.repository;

import com.eim.dataObject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public  void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("高升桥");
        orderMaster.setBuyerName("顺悟空");
        orderMaster.setBuyerOpenid("123");
        orderMaster.setBuyerPhone("159555666");
        orderMaster.setOrderAmount(new BigDecimal(10));
        orderMaster.setOrderId("123");

        OrderMaster master = repository.save(orderMaster);
        Assert.assertNotNull(master);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        String openid = "123";
        PageRequest request = new PageRequest(0,2);
        Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid(openid, request);

        Assert.assertNotEquals(0,byBuyerOpenid.getTotalElements());
    }

}