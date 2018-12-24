package com.eim.repository;

import com.eim.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类目dao
 * Created by Zy on 2018/12/7.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    //查询类目列表
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
