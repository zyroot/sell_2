package com.eim.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目） 第二层
 * Created by Zy on 2018/12/11.
 */
@Data
public class ProductVo {

    @JsonProperty(value = "name")
    private String cotegoryName;

    @JsonProperty(value = "type")
    private Integer categoryType;

    @JsonProperty(value = "foods")
    private List<ProductInfoVo> productInfoVoList;
}
