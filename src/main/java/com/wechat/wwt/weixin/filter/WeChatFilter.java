package com.wechat.wwt.weixin.filter;

import com.wechat.wwt.utils.Config;
import com.wechat.wwt.weixin.api.WeChatApiConfig;
import com.wechat.wwt.weixin.utils.Prop;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author wwt
 * @date 2017/5/27 15:40
 */
public class WeChatFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        initWeChatConfig(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
    }

    private void initWeChatConfig(FilterConfig filterConfig){
        Prop prop = new Prop("weixin.properties");
        Boolean devMode = prop.getBoolean("devMode");
        String appId = prop.get("appId");
        String appSecret = prop.get("appSecret");
        String token = prop.get("token");
        String encodingAesKey = prop.get("encodingAesKey");
        Boolean encryptMessage = prop.getBoolean("encryptMessage");
        Config.devMode = devMode;
        WeChatApiConfig weChatApiConfig = Config.weChatApiConfig;
        weChatApiConfig.setAppId(appId);
        weChatApiConfig.setAppSecret(appSecret);
        weChatApiConfig.setToken(token);
        weChatApiConfig.setEncodingAesKey(encodingAesKey);
        weChatApiConfig.setEncryptMessage(encryptMessage);
    }
}
