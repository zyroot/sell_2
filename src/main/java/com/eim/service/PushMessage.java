package com.eim.service;

import com.eim.dto.OrderMasterDto;

/**
 * Created by Zy on 2018/12/19.
 */
public interface PushMessage {

    void pushMessage(OrderMasterDto orderMasterDto);
}
