package com.eim.repository;

import com.eim.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Zy on 2018/12/7.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    //5通过商品的状态进行查询
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
