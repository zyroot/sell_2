package com.eim.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信数据配置类
 * Created by Zy on 2018/12/13.
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatPropertiesConfig {

    private String mpAppId;

    private String mpAppSecret;

    private String openAppId;

    private String openAppSecret;

    private String mchId;

    private String mchKey;

    private String keyPath;

    private String notifyUrl;

}
