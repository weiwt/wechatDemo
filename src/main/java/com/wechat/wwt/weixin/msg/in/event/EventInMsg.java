/**
 * Copyright (c) 2011-2015, Unas 小强哥 (unas@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.wechat.wwt.weixin.msg.in.event;


import com.wechat.wwt.weixin.msg.in.InMsg;

public abstract class EventInMsg extends InMsg {
    protected String event;

    public EventInMsg(String toUserName, String fromUserName, Integer createTime, String msgType, String event) {
        super(toUserName, fromUserName, createTime, msgType);
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
