package com.eim.service.impl;

import com.eim.dataObject.ProductCategory;
import com.eim.repository.ProductCategoryRepository;
import com.eim.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Zy on 2018/12/7.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

    //引入dao
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList) {
        return repository.findByCategoryTypeIn(typeList);
    }

    @Transactional
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }


    @Override
    public ProductCategory productCategory(List<ProductCategory> categoryList,Integer categoryType){
        for (ProductCategory productCategory:categoryList){
            if(productCategory.getCategoryType().equals(categoryType)){
                return productCategory;
            }
        }
        return  null;
    }
}
