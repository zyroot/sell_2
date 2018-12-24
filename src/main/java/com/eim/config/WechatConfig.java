package com.eim.config;


import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Zy on 2018/12/13.
 */
@Component
public class WechatConfig {

    //微信数据配置类
    @Autowired
    private WechatPropertiesConfig wechatPropertiesConfig;

    //注入自定义配置的wxMpService
    @Bean
    public WxMpService wxMpService(){
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    //配置项
    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wechatPropertiesConfig.getMpAppId());
        wxMpInMemoryConfigStorage.setSecret(wechatPropertiesConfig.getMpAppSecret());
        return wxMpInMemoryConfigStorage;
    }
}
