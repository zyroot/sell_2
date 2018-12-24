package com.eim.controller;

import com.eim.enums.ExceptionEnum;
import com.eim.exception.SellException;
import com.eim.util.RedirectUrlUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Zy on 2018/12/14.
 */
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private RedirectUrlUtil redirectUrlUtil;

    @Autowired
    private WxMpService wxOpenService;

    //1.书写一个最后重定向的，并接受用户信息的接口，http://192.168.160.1:8080/wechat/get.do
    @RequestMapping(value = "/userInfo",produces="text/plain;charset=UTF-8")
    public String get(WxMpUser user){
        if(StringUtils.isEmpty(user.getOpenId())){
            log.error("【微信授权】 openid为空 openid={}",user.getOpenId());
            throw new SellException(ExceptionEnum.ORDER_FORM_ERROR);
        }
        log.info("【微信授权】openid={}",user.getOpenId());
        return "redirect:http://appeim.natapp1.cc/#/?openid="+user.getOpenId();
    }

    //2.调用封装的工具类(RedirectUrlUtil)，重定向到拼装的路径
    @RequestMapping("/authorization.do")
    public String test(){
        String redirectUrl = redirectUrlUtil.redirectUrl();
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl){
        String url = "http://appeim.natapp1.cc/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN,returnUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state")String state){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        log.info("wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:"+state+"?openid="+openId;
    }

}
