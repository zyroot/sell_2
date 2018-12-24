package com.eim.controller;

import com.eim.VO.ProductInfoVo;
import com.eim.VO.ProductVo;
import com.eim.VO.ResultVO;
import com.eim.dataObject.ProductCategory;
import com.eim.dataObject.ProductInfo;
import com.eim.service.ProductCategoryService;
import com.eim.service.ProductInfoService;
import com.eim.util.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * Created by Zy on 2018/12/10.
 */
@Controller
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有的上架商品
        List<ProductInfo> upAll = productInfoService.findUpAll();
        //2.查询类目（一次性查询--性能更好）
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (int i = 0 ; i< upAll.size(); i++){
//            categoryTypeList.add(upAll.get(i).getCategoryType());
//        }
        //精简方法（lombda）
        List<Integer> typeList = upAll.stream().
                                map(e -> e.getCategoryType()).
                                collect(Collectors.toList());
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(typeList);
        //3.数据拼装
        // 1)遍历类目
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory:byCategoryTypeIn){
            ProductVo productVo = new ProductVo();
            productVo.setCotegoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for(ProductInfo productInfo:upAll){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        };
        return ResultVoUtil.success(productVoList);
    }
}
