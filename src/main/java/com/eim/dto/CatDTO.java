package com.eim.dto;

import lombok.Data;

/**
 * 购物车 数据传输对象
 * Created by Zy on 2018/12/12.
 */
@Data
public class CatDTO {

    private String produtId;

    private Integer productQuantity;

    public CatDTO() {
    }

    public CatDTO(String produtId, Integer productQuantity) {
        this.produtId = produtId;
        this.productQuantity = productQuantity;
    }
}
