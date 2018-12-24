package com.eim.service.impl;

import com.eim.dto.OrderMasterDto;
import com.eim.service.OrderService;
import com.eim.service.PushMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 推送消息模板
 * Created by Zy on 2018/12/19.
 */
@Slf4j
@Service
public class PushMessageImpl implements PushMessage {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private OrderService orderService;

    @Override
    public void pushMessage(OrderMasterDto orderMasterDto) {
        OrderMasterDto masterDto = orderService.findOne(orderMasterDto.getOrderId());
        //微信模板推送操作类
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //模板id
        wxMpTemplateMessage.setTemplateId("16O4E-Uh5885rxcf-UQfapItXdDaluexXWiZeQGN-T0");
        //发送给谁
        wxMpTemplateMessage.setToUser("orLm4watuJguhURDbZ9asX6YWs4o");
        //自定义模板消息
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","亲记得收货哦"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2",masterDto.getOrderId()),
                new WxMpTemplateData("keyword3",masterDto.getOrderAmount().toString()),
                new WxMpTemplateData("remark",masterDto.getBuyerName()+masterDto.getBuyerAddress())
        );
        //设置模板数据
        wxMpTemplateMessage.setData(data);
        try {
            //发送消息模板
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("【消息模板推送失败】");
        }
    }

}
