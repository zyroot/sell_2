package com.eim.dataObject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Zy on 2018/12/19.
 */
@Entity
@DynamicUpdate
@Data
public class SellerInfo {
    /**
     * CREATE TABLE seller_info(
     seller_id VARCHAR(32) not null,
     username VARCHAR(32) not null,
     userpwd VARCHAR(32) not null,
     openid VARCHAR(32) not null,
     create_time TIMESTAMP not NULL DEFAULT CURRENT_TIMESTAMP,
     update_time TIMESTAMP NOT null DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
     primary key (seller_id),
     unique key uni_seller_id(seller_id)
     )ENGINE=INNODB DEFAULT CHARSET=utf8 comment '买家信息表';
     */

    @Id
    private String sellerId;

    private String username;

    private String userpwd;

    private String openid;
}
