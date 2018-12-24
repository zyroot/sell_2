package com.eim.service;

import com.eim.dataObject.ProductCategory;

import java.util.List;

/**
 * Created by Zy on 2018/12/7.
 */
public interface ProductCategoryService {

    //查询一条记录
    ProductCategory findOne(Integer id);

    //查询所有
    List<ProductCategory> findAll();

    //根据类型查询
    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);

    //新增 //更新
    ProductCategory save(ProductCategory productCategory);

    ProductCategory productCategory(List<ProductCategory> categoryList,Integer categoryType);

}
