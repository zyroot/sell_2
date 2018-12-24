package com.eim.dataObject;

import com.eim.enums.ProductStatusEnum;
import com.eim.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Zy on 2018/12/7.
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "product_info")
public class ProductInfo {

    /**
     * CREATE TABLE `product_info` (
     `product_id` varchar(32) NOT NULL,
     `product_name` varchar(64) NOT NULL,
     `product_price` decimal(8,2) NOT NULL,
     `product_stock` int(11) NOT NULL COMMENT '库存',
     `product_description` varchar(64) DEFAULT NULL COMMENT '商品描述',
     `product_icon` varchar(512) DEFAULT NULL COMMENT '商品图片',
     `product_status` tinyint(3) NOT NULL DEFAULT '0',
     `category_type` int(11) NOT NULL COMMENT '商品类目',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`product_id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    @Id
    private String productId;

    /**商品名字*/
    private String productName;

    /**商品价格*/
    private BigDecimal productPrice;

    /**商品库存*/
    private Integer productStock;

    /**商品描述*/
    private String productDescription;

    /**商品小图*/
    private String productIcon;

    /**商品状态 0 正常，1 下架*/
    private Integer productStatus;

    /**商品类型编号*/
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum productStatusEnum(){
        return EnumUtil.getCode(productStatus,ProductStatusEnum.class);
    }


}
