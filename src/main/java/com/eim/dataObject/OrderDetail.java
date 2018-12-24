package com.eim.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Zy on 2018/12/11.
 */
@Entity
@Data
public class OrderDetail {

    /**
     * CREATE TABLE `order_detail` (
     `detail_id` varchar(64) NOT NULL,
     `order_id` varchar(32) NOT NULL,
     `product_id` varchar(32) NOT NULL,
     `product_name` varchar(32) NOT NULL COMMENT '商品名字',
     `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
     `product_quantity` int(11) NOT NULL,
     `product_icon` varchar(64) NOT NULL,
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`detail_id`),
     KEY `idx_order_id` (`order_id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */
    @Id
    private String detailId;

    /** 订单id */
    private String orderId;

    /** 商品id */
    private String productId;

    /** 商品名字 */
    private  String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品数量 */
    private Integer productQuantity;

    /** 商品小图 */
    private String productIcon;

//    private Date createTime;
//
//    private Date updateTime;

}
