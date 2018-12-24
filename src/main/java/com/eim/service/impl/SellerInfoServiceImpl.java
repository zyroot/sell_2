package com.eim.service.impl;

import com.eim.dataObject.SellerInfo;
import com.eim.repository.SellerInfoRepository;
import com.eim.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zy on 2018/12/19.
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        SellerInfo sellerInfo = repository.findByOpenid(openid);
        return sellerInfo;
    }
}
