package com.eim.controller;

import com.eim.dataObject.ProductCategory;
import com.eim.dataObject.ProductInfo;
import com.eim.dto.OrderMasterDto;
import com.eim.enums.ExceptionEnum;
import com.eim.enums.ProductStatusEnum;
import com.eim.exception.SellException;
import com.eim.form.ProductInfoForm;
import com.eim.service.ProductCategoryService;
import com.eim.service.ProductInfoService;
import com.eim.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Zy on 2018/12/18.
 */
@Slf4j
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "size",defaultValue = "10")Integer size,
                       Map map){
        Page<ProductInfo> productInfoPage = productInfoService.findAll(new PageRequest((page-1), size));
        List<ProductCategory> categoryList = productCategoryService.findAll();
//        ProductCategory productCategory = productCategoryService.productCategory(categoryList, productInfo.getCategoryType());
        map.put("categoryList",categoryList);
        map.put("productInfoPage",productInfoPage);
        map.put("size",size);
        return "product/list";
    }

    @GetMapping("/up_off")
    public String up_off(@RequestParam("productId")String productId,Map map){

        try {
            productInfoService.up_off(productId);
        } catch (Exception e) {
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
            return "common/error";
        }

        map.put("msg", ExceptionEnum.SUCCESS.getMessage());
        map.put("url","/seller/product/list");
        return "common/success";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("productId")String productId,Map map){

        ProductInfo productInfo = productInfoService.findOne(productId);
        if(productInfo == null){
            log.error("【修改回显】 商品不存在 productInfo={}",productInfo);
            map.put("msg",ExceptionEnum.PRODUCT_NOT_EXIST.getMessage());
            map.put("url","/seller/product/list");
            return "common/error";
        }

        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        map.put("productInfo",productInfo);
        return "product/edit";
    }

    @PostMapping("/doEdit")
    public String doEdit(@Valid ProductInfoForm productInfoForm, BindingResult bindingResult, Map map){
            if(bindingResult.hasErrors()){
                log.error("【商品修改】 参数不正确productInfo={}",productInfoForm);
                map.put("msg",bindingResult.getFieldError().getDefaultMessage());
                map.put("url","/seller/product/list");
                return "common/error";
            }
            ProductInfo productInfo = new ProductInfo();
            BeanUtils.copyProperties(productInfoForm,productInfo);
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            try {
                productInfoService.save(productInfo);
            } catch (Exception e) {
                map.put("msg",ExceptionEnum.PRODUCT_FORM_ERROR.getMessage());
                map.put("url","/seller/product/list");
                return "common/error";
            }
             map.put("msg",ExceptionEnum.PRODUCT_EDIT_SUCCESS.getMessage());
             map.put("url","/seller/product/list");
            return "common/success";
    }

    /**
     * 增加页面
     * @param map
     * @return
     */
    @GetMapping("/index")
    public String addPage(Map map){
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        return "product/add";
    }

    @PostMapping("/doAdd")
    public String doAdd(ProductInfo productInfoForm,  Map map){
//        if(bindingResult.hasErrors()){
//            log.error("【商品修改】 参数不正确productInfo={}",productInfoForm);
//            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
//            map.put("url","/seller/product/list");
//            return "common/error";
//        }
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productInfoForm,productInfo);
        productInfo.setProductId(KeyUtil.uniKey());
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        try {
            productInfoService.save(productInfo);
        } catch (Exception e) {
            map.put("msg",ExceptionEnum.PRODUCT_FORM_ERROR.getMessage());
            map.put("url","/seller/product/list");
            return "common/error";
        }
        map.put("msg",ExceptionEnum.PRODUCT_SAVE_SUCCESS.getMessage());
        map.put("url","/seller/product/list");
        return "common/success";
    }
}
