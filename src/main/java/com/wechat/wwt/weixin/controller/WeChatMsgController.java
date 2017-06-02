package com.wechat.wwt.weixin.controller;

import com.wechat.wwt.log.Log;
import com.wechat.wwt.weixin.api.ApiConfigKit;
import com.wechat.wwt.weixin.api.ContentType;
import com.wechat.wwt.weixin.msg.in.InImageMsg;
import com.wechat.wwt.weixin.msg.in.InLinkMsg;
import com.wechat.wwt.weixin.msg.in.InLocationMsg;
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
import com.wechat.wwt.weixin.msg.out.OutTextMsg;
import com.wechat.wwt.weixin.msg.out.OutVoiceMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wwt
 * @date 2017/5/19 10:53
 */
@Controller
@RequestMapping("wechat/api")
public class WeChatMsgController extends MsgController{

    static Log logger = Log.getLog(WeChatMsgController.class);

    @RequestMapping("msg")
    public void  message(HttpServletRequest request, HttpServletResponse response)throws Exception{
        index(request,response);
    }

    @Override
    protected void processInTextMsg(InTextMsg inTextMsg) {
        String content = inTextMsg.getContent();
        renderOutTextMsg(content);
    }

    @Override
    protected void processInImageMsg(InImageMsg inImageMsg) {

    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {

    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {

    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {

    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {

    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {

    }

    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {

    }

    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {

    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {

    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {

    }

    @Override
    protected void processInMassEvent(InMassEvent inMassEvent) {

    }

    @Override
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {

    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {

    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

    }

    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

    }

    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

    }

    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {

    }

    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {

    }

    @Override
    protected void processInWifiEvent(InWifiEvent inWifiEvent) {

    }

    @Override
    protected void processInUserViewCardEvent(InUserViewCardEvent inUserViewCardEvent) {

    }

    @Override
    protected void processInSubmitMemberCardEvent(InSubmitMemberCardEvent inSubmitMemberCardEvent) {

    }

    @Override
    protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent) {

    }

    @Override
    protected void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent) {

    }

    @Override
    protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {

    }

    @Override
    protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {

    }

    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {

    }
}
