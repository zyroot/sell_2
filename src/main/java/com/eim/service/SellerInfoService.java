package com.eim.service;

import com.eim.dataObject.SellerInfo;

/**
 * 卖家端service
 * Created by Zy on 2018/12/19.
 */
public interface SellerInfoService {

    SellerInfo findByOpenid(String openid);

}
