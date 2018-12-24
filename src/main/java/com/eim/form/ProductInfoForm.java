package com.eim.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Zy on 2018/12/19.
 */
@Data
public class ProductInfoForm {

    @NotEmpty(message = "商品id不能为空")
    private String productId;

    /**商品名字*/
    @NotEmpty(message = "商品名字不能为空")
    private String productName;

    /**商品价格*/
    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    /**商品库存*/
    @NotNull(message = "商品库存不能为空")
    private Integer productStock;

    /**商品描述*/
    @NotEmpty(message = "商品描述不能为空")
    private String productDescription;

    /**商品小图*/
//    @NotEmpty(message = "商品图片不能为空")
    private String productIcon;

    /**商品状态 0 正常，1 下架*/
    private Integer productStatus;

    /**商品类型编号*/
    @NotNull(message = "类目不能为空")
    private Integer categoryType;


}
