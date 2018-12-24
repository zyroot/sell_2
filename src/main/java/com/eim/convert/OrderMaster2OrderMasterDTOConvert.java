package com.eim.convert;

import com.eim.dataObject.OrderMaster;
import com.eim.dto.OrderMasterDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 * Created by Zy on 2018/12/12.
 */
public class OrderMaster2OrderMasterDTOConvert {

    public static OrderMasterDto convert(OrderMaster orderMaster){
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        BeanUtils.copyProperties(orderMaster,orderMasterDto);
        return orderMasterDto;
    }
    public static List<OrderMasterDto> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
            convert(e)
        ).collect(Collectors.toList());
    }
}

