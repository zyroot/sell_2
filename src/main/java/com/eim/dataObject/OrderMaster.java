package com.eim.dataObject;

import com.eim.enums.OrderStatusEnum;
import com.eim.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * Created by Zy on 2018/12/11.
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    /**
     * CREATE TABLE `order_master` (
     `order_id` varchar(32) NOT NULL,
     `buyer_name` varchar(11) NOT NULL,
     `buyer_phone` varchar(32) NOT NULL,
     `buyer_address` varchar(128) NOT NULL,
     `buyer_openid` varchar(64) NOT NULL,
     `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
     `order_status` tinyint(3) NOT NULL COMMENT '订单状态',
     `pay_status` tinyint(3) NOT NULL COMMENT '支付状态',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`order_id`),
     KEY `idx_order_id` (`order_id`) USING BTREE
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    /** 订单id */
    @Id
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 默认为新下单（0），*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态 默认是未支付（0）*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    private Date create_time;
    private Date update_time;


}
