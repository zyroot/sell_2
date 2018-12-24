package com.eim.repository;

import com.eim.dataObject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zy on 2018/12/19.
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}
