package com.wechat.wwt.weixin.controller;

import com.wechat.wwt.weixin.api.WeChatMsgManager;
import com.wechat.wwt.weixin.msg.out.OutTextMsg;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wwt
 * @date 2017/5/31 9:46
 */
public abstract class Controller {

    ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
    ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();

    private ThreadLocal<WeChatMsgManager> weChatMsgManagerThreadLocal = new ThreadLocal<WeChatMsgManager>();

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response){
        requestThreadLocal.set(request);
        responseThreadLocal.set(response);
        initWeChatMsgManager();
    }

    private void initWeChatMsgManager(){
        HttpServletRequest request = requestThreadLocal.get();
        HttpServletResponse response = responseThreadLocal.get();
        WeChatMsgManager weChatMsgManager = new WeChatMsgManager(request,response);
        weChatMsgManagerThreadLocal.set(weChatMsgManager);
    }

    public WeChatMsgManager getWeChatMsgManager(){
        WeChatMsgManager weChatMsgManager = weChatMsgManagerThreadLocal.get();
        return weChatMsgManager;
    }

    public void renderOutTextMsg(String content) {
        getWeChatMsgManager().renderOutTextMsg(content);
    }
}
