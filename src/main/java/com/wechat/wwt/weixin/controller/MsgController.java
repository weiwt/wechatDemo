/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.wechat.wwt.weixin.controller;


import com.wechat.wwt.log.Log;
import com.wechat.wwt.utils.Config;
import com.wechat.wwt.weixin.api.WeChatMsgManager;
import com.wechat.wwt.weixin.msg.in.InImageMsg;
import com.wechat.wwt.weixin.msg.in.InLinkMsg;
import com.wechat.wwt.weixin.msg.in.InLocationMsg;
import com.wechat.wwt.weixin.msg.in.InMsg;
import com.wechat.wwt.weixin.msg.in.InNotDefinedMsg;
import com.wechat.wwt.weixin.msg.in.InShortVideoMsg;
import com.wechat.wwt.weixin.msg.in.InTextMsg;
import com.wechat.wwt.weixin.msg.in.InVideoMsg;
import com.wechat.wwt.weixin.msg.in.InVoiceMsg;
import com.wechat.wwt.weixin.msg.in.event.InCustomEvent;
import com.wechat.wwt.weixin.msg.in.event.InFollowEvent;
import com.wechat.wwt.weixin.msg.in.event.InLocationEvent;
import com.wechat.wwt.weixin.msg.in.event.InMassEvent;
import com.wechat.wwt.weixin.msg.in.event.InMenuEvent;
import com.wechat.wwt.weixin.msg.in.event.InMerChantOrderEvent;
import com.wechat.wwt.weixin.msg.in.event.InNotDefinedEvent;
import com.wechat.wwt.weixin.msg.in.event.InPoiCheckNotifyEvent;
import com.wechat.wwt.weixin.msg.in.event.InQrCodeEvent;
import com.wechat.wwt.weixin.msg.in.event.InShakearoundUserShakeEvent;
import com.wechat.wwt.weixin.msg.in.event.InSubmitMemberCardEvent;
import com.wechat.wwt.weixin.msg.in.event.InTemplateMsgEvent;
import com.wechat.wwt.weixin.msg.in.event.InUpdateMemberCardEvent;
import com.wechat.wwt.weixin.msg.in.event.InUserPayFromCardEvent;
import com.wechat.wwt.weixin.msg.in.event.InUserViewCardEvent;
import com.wechat.wwt.weixin.msg.in.event.InVerifyFailEvent;
import com.wechat.wwt.weixin.msg.in.event.InVerifySuccessEvent;
import com.wechat.wwt.weixin.msg.in.event.InWifiEvent;
import com.wechat.wwt.weixin.msg.in.speech_recognition.InSpeechRecognitionResults;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接收微信服务器消息，自动解析成 InMsg 并分发到相应的处理方法
 */
public abstract class MsgController extends Controller{


    private static final Log log = Log.getLog(MsgController.class);


    /**
     * weixin 公众号服务器调用唯一入口，即在开发者中心输入的 URL 必须要指向此 action
     */
    public void index(HttpServletRequest request, HttpServletResponse response) {
//        init(request, response);

        WeChatMsgManager weChatMsgManager = getWeChatMsgManager();
        // 如果是服务器配置请求，则配置服务器并返回
        if (weChatMsgManager.isConfigServerRequest()) {
            weChatMsgManager.configServer();
            return;
        }

        // 解析消息并根据消息类型分发到相应的处理方法
        InMsg msg = weChatMsgManager.getInMsg();
        if (msg instanceof InTextMsg)
            processInTextMsg((InTextMsg) msg);
        else if (msg instanceof InImageMsg)
            processInImageMsg((InImageMsg) msg);
        else if (msg instanceof InSpeechRecognitionResults)  //update by unas at 2016-1-29, 由于继承InVoiceMsg，需要在InVoiceMsg前判断类型
            processInSpeechRecognitionResults((InSpeechRecognitionResults) msg);
        else if (msg instanceof InVoiceMsg)
            processInVoiceMsg((InVoiceMsg) msg);
        else if (msg instanceof InVideoMsg)
            processInVideoMsg((InVideoMsg) msg);
        else if (msg instanceof InShortVideoMsg)   //支持小视频
            processInShortVideoMsg((InShortVideoMsg) msg);
        else if (msg instanceof InLocationMsg)
            processInLocationMsg((InLocationMsg) msg);
        else if (msg instanceof InLinkMsg)
            processInLinkMsg((InLinkMsg) msg);
        else if (msg instanceof InCustomEvent)
            processInCustomEvent((InCustomEvent) msg);
        else if (msg instanceof InFollowEvent)
            processInFollowEvent((InFollowEvent) msg);
        else if (msg instanceof InQrCodeEvent)
            processInQrCodeEvent((InQrCodeEvent) msg);
        else if (msg instanceof InLocationEvent)
            processInLocationEvent((InLocationEvent) msg);
        else if (msg instanceof InMassEvent)
            processInMassEvent((InMassEvent) msg);
        else if (msg instanceof InMenuEvent)
            processInMenuEvent((InMenuEvent) msg);
        else if (msg instanceof InTemplateMsgEvent)
            processInTemplateMsgEvent((InTemplateMsgEvent) msg);
        else if (msg instanceof InShakearoundUserShakeEvent)
            processInShakearoundUserShakeEvent((InShakearoundUserShakeEvent) msg);
        else if (msg instanceof InVerifySuccessEvent)
            processInVerifySuccessEvent((InVerifySuccessEvent) msg);
        else if (msg instanceof InVerifyFailEvent)
            processInVerifyFailEvent((InVerifyFailEvent) msg);
        else if (msg instanceof InPoiCheckNotifyEvent)
            processInPoiCheckNotifyEvent((InPoiCheckNotifyEvent) msg);
        else if (msg instanceof InWifiEvent)
            processInWifiEvent((InWifiEvent) msg);
        else if (msg instanceof InUserViewCardEvent)
            processInUserViewCardEvent((InUserViewCardEvent) msg);
        else if (msg instanceof InSubmitMemberCardEvent)
            processInSubmitMemberCardEvent((InSubmitMemberCardEvent) msg);
        else if (msg instanceof InUpdateMemberCardEvent)
            processInUpdateMemberCardEvent((InUpdateMemberCardEvent) msg);
        else if (msg instanceof InUserPayFromCardEvent)
            processInUserPayFromCardEvent((InUserPayFromCardEvent) msg);
        else if (msg instanceof InMerChantOrderEvent)
            processInMerChantOrderEvent((InMerChantOrderEvent) msg);
        else if (msg instanceof InNotDefinedEvent) {
            log.error("未能识别的事件类型。 消息 xml 内容为：\n" + weChatMsgManager.getInMsgXml());
            processIsNotDefinedEvent((InNotDefinedEvent) msg);
        } else if (msg instanceof InNotDefinedMsg) {
            log.error("未能识别的消息类型。 消息 xml 内容为：\n" + weChatMsgManager.getInMsgXml());
            processIsNotDefinedMsg((InNotDefinedMsg) msg);
        }
    }



    // 处理接收到的文本消息
    protected abstract void processInTextMsg(InTextMsg inTextMsg);

    // 处理接收到的图片消息
    protected abstract void processInImageMsg(InImageMsg inImageMsg);

    // 处理接收到的语音消息
    protected abstract void processInVoiceMsg(InVoiceMsg inVoiceMsg);

    // 处理接收到的视频消息
    protected abstract void processInVideoMsg(InVideoMsg inVideoMsg);

    // 处理接收到的视频消息
    protected abstract void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg);

    // 处理接收到的地址位置消息
    protected abstract void processInLocationMsg(InLocationMsg inLocationMsg);

    // 处理接收到的链接消息
    protected abstract void processInLinkMsg(InLinkMsg inLinkMsg);

    // 处理接收到的多客服管理事件
    protected abstract void processInCustomEvent(InCustomEvent inCustomEvent);

    // 处理接收到的关注/取消关注事件
    protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);

    // 处理接收到的扫描带参数二维码事件
    protected abstract void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent);

    // 处理接收到的上报地理位置事件
    protected abstract void processInLocationEvent(InLocationEvent inLocationEvent);

    // 处理接收到的群发任务结束时通知事件
    protected abstract void processInMassEvent(InMassEvent inMassEvent);

    // 处理接收到的自定义菜单事件
    protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);

    // 处理接收到的语音识别结果
    protected abstract void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults);

    // 处理接收到的模板消息是否送达成功通知事件
    protected abstract void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent);

    // 处理微信摇一摇事件
    protected abstract void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent);

    // 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
    protected abstract void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent);

    // 资质认证失败 || 名称认证失败
    protected abstract void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent);

    // 门店在审核事件消息
    protected abstract void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent);

    // WIFI连网后下发消息 by unas at 2016-1-29
    protected abstract void processInWifiEvent(InWifiEvent inWifiEvent);

    // 微信会员卡二维码扫描领取接口
    protected abstract void processInUserViewCardEvent(InUserViewCardEvent inUserViewCardEvent);

    // 微信会员卡激活接口
    protected abstract void processInSubmitMemberCardEvent(InSubmitMemberCardEvent inSubmitMemberCardEvent);

    // 微信会员卡积分变更
    protected abstract void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent);

    // 微信会员卡快速买单
    protected abstract void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent);

    // 微信小店订单支付成功接口消息
    protected abstract void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent);

    // 没有找到对应的事件消息
    protected abstract void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent);

    // 没有找到对应的消息
    protected abstract void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg);


}













