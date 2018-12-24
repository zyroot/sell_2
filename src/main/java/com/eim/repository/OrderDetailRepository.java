package com.eim.repository;

import com.eim.dataObject.OrderDetail;
import com.eim.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Zy on 2018/12/11.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    //根据订单id 查询 订单详情（1对多）
    List<OrderDetail> findByOrderId(String orderId);


}
