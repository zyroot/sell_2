package com.eim.service.impl;

import com.eim.dataObject.ProductInfo;
import com.eim.dto.CatDTO;
import com.eim.enums.ExceptionEnum;
import com.eim.enums.ProductStatusEnum;
import com.eim.exception.SellException;
import com.eim.repository.ProductInfoRepository;
import com.eim.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品详情service
 * Created by Zy on 2018/12/7.
 */
@Slf4j
@Service
public class ProductInfoServiceImpl implements ProductInfoService{

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String id) {
        return repository.findOne(id);
    }

    /**
     * 查询所有上架商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 查询所有带分页
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
       return repository.findAll(pageable);
    }

    /**
     * 根据状态查询
     * @param productStatus
     * @return
     */
    @Override
    public List<ProductInfo> findByStatus(Integer productStatus) {
        return repository.findByProductStatus(productStatus);
    }

    @Transactional
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    /**
     * 加库存
     * @param catDTOList
     */
    @Transactional
    @Override
    public void increaseStock(List<CatDTO> catDTOList) {
        for (CatDTO catDTO : catDTOList) {
            ProductInfo productInfo = repository.findOne(catDTO.getProdutId());
            if(productInfo == null){
                log.error("【商品详情加库存】 productInfo={}",productInfo);
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() + catDTO.getProductQuantity();
            //保存入库
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 减库存
     * @param catDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CatDTO> catDTOList) {
        for (CatDTO catDTO : catDTOList) {
            ProductInfo productInfo = repository.findOne(catDTO.getProdutId());
            if(productInfo == null){
                log.error("【商品详情减库存】 productInfo={}",productInfo);
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - catDTO.getProductQuantity();
            if(result < 0 ){
                log.error("【商品详情减库存】 result={}",result);
                throw new SellException(ExceptionEnum.PRODUCT_NOT_ENOUGH);
            }
            //保存入库
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 商品上下架
     * @param productId
     */
    @Override
    public void up_off(String productId) {
        ProductInfo one = repository.findOne(productId);
        if (one == null){
            log.error("【商品上下架】商品不存在 product={}",one);
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if(one.getProductStatus().equals(ProductStatusEnum.UP.getCode())){
            //下架操作
            one.setProductStatus(ProductStatusEnum.DOWN.getCode());
            repository.save(one);
        }else{
            //上架操作
            one.setProductStatus(ProductStatusEnum.UP.getCode());
            repository.save(one);
        }
    }
}
