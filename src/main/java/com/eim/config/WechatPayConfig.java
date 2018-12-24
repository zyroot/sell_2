package com.eim.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * BESTPAY
 * 配置类
 * Created by Zy on 2018/12/14.
 */
@Component
public class WechatPayConfig {


    @Autowired
    private WechatPropertiesConfig wechatPropertiesConfig;

    /**
     * 引入自定义bestPayService
     * @return
     */
    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return  bestPayService;
    }

    /**
     * 引入配置项
     * @return
     */
    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        //公众号id
        wxPayH5Config.setAppId(wechatPropertiesConfig.getMpAppId());
        //公众号appsecret
        wxPayH5Config.setAppSecret(wechatPropertiesConfig.getMpAppSecret());
        //商户id
        wxPayH5Config.setMchId(wechatPropertiesConfig.getMchId());
        //商户秘钥
        wxPayH5Config.setMchKey(wechatPropertiesConfig.getMchKey());
        //商户证书地址
        wxPayH5Config.setKeyPath(wechatPropertiesConfig.getKeyPath());
        //回调地址
        wxPayH5Config.setNotifyUrl(wechatPropertiesConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
