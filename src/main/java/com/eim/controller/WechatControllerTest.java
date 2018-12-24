package com.eim.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Zy on 2018/12/13.
 */
@Slf4j
@Controller
@RequestMapping("/wechat/test")
public class WechatControllerTest {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 1、
     * 首先构造网页授权url，然后构成超链接让用户点击：
     * @param returnUrl
     * @return
     */
    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){
        //1.配置
        //2.调用
        String url = "http://wechat.ei-marketing.net/sell/wechat/userInfo";
        /**
         * url : 重定向接收code 和 携带参数 state的 路径
         * WxConsts.OAuth2Scope.SNSAPI_USERINFO ：授权类型
         * returnUrl ：携带的参数
         */
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);
        log.info("redirectUrlt={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    /**
     * 2、
     * 当用户同意授权后，会回调所设置的url并把authorization code传过来，
     * 然后用这个code获得access token，其中也包含用户的openid等信息
     * @param code
     * @param state
     */
    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,@RequestParam("state")String state){
        log.info("获取到了code={}",code);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        WxMpUser wxMpUser = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        if(wxMpOAuth2AccessToken == null){
          log.error("【获取accessToken】token为空， accessToken={}",wxMpOAuth2AccessToken);
        }
        if(wxMpUser == null){
          log.error("【获取wxMpUser】对象为空， wxMpUser={}",wxMpUser);
        }
        //获取昵称
        wxMpUser.getNickname();
        //openid
        wxMpUser.getOpenId();
        //获取省
        wxMpUser.getProvince();
        //市
        wxMpUser.getCity();
        //国家
        wxMpUser.getCountry();
        //头像
        wxMpUser.getHeadImgUrl();
        //语言
        wxMpUser.getLanguage();
        //性别
        wxMpUser.getSex();
        return "redirect:"+state+"?openid="+wxMpUser.getOpenId();
    }

}
