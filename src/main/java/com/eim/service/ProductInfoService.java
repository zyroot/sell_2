package com.eim.service;

import com.eim.dataObject.ProductInfo;
import com.eim.dto.CatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Zy on 2018/12/7.
 */
public interface ProductInfoService {

    //查询一个
    ProductInfo findOne(String id);
    //查询所有上架商品
    List<ProductInfo> findUpAll();
    //查询所有 分页
    Page<ProductInfo> findAll(Pageable pageable);
    //根据状体查询商品
    List<ProductInfo> findByStatus(Integer productStatus);
    //更新 保存
    ProductInfo save(ProductInfo productInfo);
    //加库存(传入 id 和 quantity)
    void increaseStock(List<CatDTO> catDTOList);
    //减库存
    void decreaseStock(List<CatDTO> catDTOList);

    //上下架
    void up_off(String productId);
}
