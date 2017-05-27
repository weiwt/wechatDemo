package com.wechat.wwt.weixin.api;

import java.io.Serializable;

/**
 * @author wwt
 * @date 2017/5/23 15:22
 */
public class WeChatApiConfig implements Serializable {
    private String token = null;
    private String appId = null;
    private String appSecret = null;
    private String encodingAesKey = null;
    private boolean messageEncrypt = false;	// 消息加密与否

    public WeChatApiConfig(){
    }

    public WeChatApiConfig(String token) {
        setToken(token);
    }

    public WeChatApiConfig(String token, String appId, String appSecret) {
        setToken(token);
        setAppId(appId);
        setAppSecret(appSecret);
    }

    public WeChatApiConfig(String token, String appId, String appSecret, boolean messageEncrypt, String encodingAesKey) {
        setToken(token);
        setAppId(appId);
        setAppSecret(appSecret);
        setEncryptMessage(messageEncrypt);
        setEncodingAesKey(encodingAesKey);
    }

    public String getToken() {
        if (token == null)
            throw new IllegalStateException("token 未被赋值");
        return token;
    }

    public void setToken(String token) {
        if (token == null)
            throw new IllegalArgumentException("token 值不能为 null");
        this.token = token;
    }

    public String getAppId() {
        if (appId == null)
            throw new IllegalStateException("appId 未被赋值");
        return appId;
    }

    public void setAppId(String appId) {
        if (appId == null)
            throw new IllegalArgumentException("appId 值不能为 null");
        this.appId = appId;
    }

    public String getAppSecret() {
        if (appSecret == null)
            throw new IllegalStateException("appSecret 未被赋值");
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        if (appSecret == null)
            throw new IllegalArgumentException("appSecret 值不能为 null");
        this.appSecret = appSecret;
    }

    public String getEncodingAesKey() {
        if (encodingAesKey == null)
            throw new IllegalStateException("encodingAesKey 未被赋值");
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        if (encodingAesKey == null)
            throw new IllegalArgumentException("encodingAesKey 值不能为 null");
        this.encodingAesKey = encodingAesKey;
    }

    public boolean isEncryptMessage() {
        return messageEncrypt;
    }

    /**
     * @param messageEncrypt
     * 是否对消息进行加密，对应于微信平台的消息加解密方式：
     * 1：true进行加密且必须配置 encodingAesKey
     * 2：false采用明文模式，同时也支持混合模式
     */
    public void setEncryptMessage(boolean messageEncrypt) {
        this.messageEncrypt = messageEncrypt;
    }

}
