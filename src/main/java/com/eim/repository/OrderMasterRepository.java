package com.eim.repository;

import com.eim.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zy on 2018/12/11.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{

    //查询某个人 的订单 且分页
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
