package com.eim.repository;

import com.eim.dataObject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/7.
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() throws Exception {

        List<ProductInfo> byProductStatus = productInfoRepository.findByProductStatus(0);
        log.info("【商品详情测试】 productList={}",byProductStatus);
        Assert.assertNotEquals(0,byProductStatus.size());
    }

    //新增
    @Test
    public void addOne() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("火锅");
        productInfo.setCategoryType(4);
        productInfo.setProductDescription("好好吃的火锅");
        productInfo.setProductIcon("http://");
        productInfo.setProductPrice(new BigDecimal(12));
        productInfo.setProductStatus(0);
        productInfo.setProductStock(20);

        ProductInfo save = productInfoRepository.save(productInfo);
        Assert.assertNotEquals(null,save);
    }

}