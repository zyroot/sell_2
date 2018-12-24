package com.eim.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 创建订单
 * 表单验证
 * Created by Zy on 2018/12/13.
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "电话必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;

}
