package com.eim.service.impl;

import com.eim.dataObject.ProductInfo;
import com.eim.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;


    @Test
    public void findOne() throws Exception {
        ProductInfo one = productInfoService.findOne("1");
        Assert.assertNotNull(one);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductInfo> byStatus = productInfoService.findByStatus(ProductStatusEnum.UP.getCode());
        Assert.assertNotEquals(0,byStatus);
    }

    @Test
    public void findAll1() throws Exception {
        PageRequest request = new PageRequest(0,1);
        Page<ProductInfo> all = productInfoService.findAll(request);
        System.out.println(all.getTotalPages());
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    public void findByStatus() throws Exception {

    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1235556");
        productInfo.setProductName("海鲜");
        productInfo.setCategoryType(9);
        productInfo.setProductDescription("好好吃的火锅");
        productInfo.setProductIcon("http://");
        productInfo.setProductPrice(new BigDecimal(12));
        productInfo.setProductStatus(0);
        productInfo.setProductStock(20);

        ProductInfo save = productInfoService.save(productInfo);
        System.out.println(save);
        Assert.assertNotNull(save);

    }

}