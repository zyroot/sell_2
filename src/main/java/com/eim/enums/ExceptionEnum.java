package com.eim.enums;

import lombok.Getter;

/**
 * 异常枚举
 * Created by Zy on 2018/12/11.
 */
@Getter
public enum ExceptionEnum {

    SUCCESS(0,"操作成功"),
    ORDER_FORM_ERROR(1,"参数不正确"),
    PRODUCT_FORM_ERROR(2,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_NOT_ENOUGH(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    UPDATE_ERROR(15,"更新失败"),
    PAY_STATUS_ERROR(16,"支付状态不正确"),
    CAR_EMPTY(17,"购物车不能为空"),
    ORDER_OWRN_ERROR(18,"订单归属不正确"),
    ORDER_AMOUNT_ERROR(19,"订单金额不正确"),
    ORDER_CANCEL_SUCCESS(20,"订单取消成功"),
    ORDER_FINNISH_SUCCESS(21,"完结订单成功"),
    PRODUCT_EDIT_SUCCESS(22,"商品修改成功"),
    PRODUCT_SAVE_SUCCESS(23,"商品保存成功"),
    ;

    private Integer code;

    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
