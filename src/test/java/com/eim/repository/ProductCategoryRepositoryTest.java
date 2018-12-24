package com.eim.repository;

import com.eim.dataObject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findone(){
        ProductCategory one = repository.findOne(1);
        System.out.println(one);
    }
    @Test
    public void addone(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("小pi孩最爱");
        productCategory.setCategoryType(7);
        ProductCategory save = repository.save(productCategory);
        System.out.println(save);
        Assert.assertNotEquals(null,save);
    }
    @Test
    public void updaeone(){
        ProductCategory one = repository.findOne(3);
        one.setCategoryName("呵呵哒123");
        repository.save(one);
    }

    @Test
    public void findTypeList(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }

}