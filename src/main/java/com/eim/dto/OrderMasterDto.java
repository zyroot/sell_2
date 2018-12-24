package com.eim.dto;

import com.eim.dataObject.OrderDetail;
import com.eim.enums.OrderStatusEnum;
import com.eim.enums.PayStatusEnum;
import com.eim.serialize.Date2LongSerialize;
import com.eim.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据传输层
 * Created by Zy on 2018/12/11.
 */
@Data
public class OrderMasterDto {

    /** 订单id */
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
    private Integer orderStatus ;

    /** 支付状态 默认是未支付（0）*/
    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerialize.class)
    private Date create_time;

    @JsonSerialize(using = Date2LongSerialize.class)
    private Date update_time;

    /**  购物车. */
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getCode(payStatus,PayStatusEnum.class);
    }

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getCode(orderStatus,OrderStatusEnum.class);
    }

}
