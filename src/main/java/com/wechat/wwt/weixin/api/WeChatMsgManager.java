package com.wechat.wwt.weixin.api;

import com.wechat.wwt.utils.Config;
import com.wechat.wwt.weixin.HttpKit;
import com.wechat.wwt.weixin.msg.InMsgParser;
import com.wechat.wwt.weixin.msg.in.InMsg;
import com.wechat.wwt.weixin.msg.out.OutMsg;
import com.wechat.wwt.weixin.msg.out.OutTextMsg;
import com.wechat.wwt.weixin.utils.LogKit;
import com.wechat.wwt.weixin.utils.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author wwt
 * @date 2017/5/23 16:48
 */
public class WeChatMsgManager {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private String inMsgXml = null;
    private InMsg inMsg = null;

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.TEXT;

    public WeChatMsgManager(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Stores an attribute in this request
     *
     * @param name  a String specifying the name of the attribute
     * @param value the Object to be stored
     */
    public WeChatMsgManager setAttr(String name, Object value) {
        request.setAttribute(name, value);
        return this;
    }

    /**
     * Removes an attribute from this request
     *
     * @param name a String specifying the name of the attribute to remove
     */
    public WeChatMsgManager removeAttr(String name) {
        request.removeAttribute(name);
        return this;
    }

    /**
     * Stores attributes in this request, key of the map as attribute name and value of the map as attribute value
     *
     * @param attrMap key and value as attribute of the map to be stored
     */
    public WeChatMsgManager setAttrs(Map<String, Object> attrMap) {
        for (Map.Entry<String, Object> entry : attrMap.entrySet())
            request.setAttribute(entry.getKey(), entry.getValue());
        return this;
    }

    /**
     * Returns the value of a request parameter as a String, or null if the parameter does not exist.
     * <p>
     * You should only use this method when you are sure the parameter has only one value. If the
     * parameter might have more than one value, use getParaValues(java.lang.String).
     * <p>
     * If you use this method with a multivalued parameter, the value returned is equal to the first
     * value in the array returned by getParameterValues.
     *
     * @param name a String specifying the name of the parameter
     * @return a String representing the single value of the parameter
     */
    public String getPara(String name) {
        return request.getParameter(name);
    }

    /**
     * Returns the value of a request parameter as a String, or default value if the parameter does not exist.
     *
     * @param name         a String specifying the name of the parameter
     * @param defaultValue a String value be returned when the value of parameter is null
     * @return a String representing the single value of the parameter
     */
    public String getPara(String name, String defaultValue) {
        String result = request.getParameter(name);
        return result != null && !"".equals(result) ? result : defaultValue;
    }

    /**
     * Returns the values of the request parameters as a Map.
     *
     * @return a Map contains all the parameters name and value
     */
    public Map<String, String[]> getParaMap() {
        return request.getParameterMap();
    }

    /**
     * Returns an Enumeration of String objects containing the names of the parameters
     * contained in this request. If the request has no parameters, the method returns
     * an empty Enumeration.
     *
     * @return an Enumeration of String objects, each String containing the name of
     * a request parameter; or an empty Enumeration if the request has no parameters
     */
    public Enumeration<String> getParaNames() {
        return request.getParameterNames();
    }

    /**
     * Returns an array of String objects containing all of the values the given request
     * parameter has, or null if the parameter does not exist. If the parameter has a
     * single value, the array has a length of 1.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return an array of String objects containing the parameter's values
     */
    public String[] getParaValues(String name) {
        return request.getParameterValues(name);
    }

    /**
     * Returns an array of Integer objects containing all of the values the given request
     * parameter has, or null if the parameter does not exist. If the parameter has a
     * single value, the array has a length of 1.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return an array of Integer objects containing the parameter's values
     */
    public Integer[] getParaValuesToInt(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null)
            return null;
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Integer.parseInt(values[i]);
        return result;
    }

    public Long[] getParaValuesToLong(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null)
            return null;
        Long[] result = new Long[values.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Long.parseLong(values[i]);
        return result;
    }

    /**
     * Returns an Enumeration containing the names of the attributes available to this request.
     * This method returns an empty Enumeration if the request has no attributes available to it.
     *
     * @return an Enumeration of strings containing the names of the request's attributes
     */
    public Enumeration<String> getAttrNames() {
        return request.getAttributeNames();
    }

    /**
     * Returns the value of the named attribute as an Object, or null if no attribute of the given name exists.
     *
     * @param name a String specifying the name of the attribute
     * @return an Object containing the value of the attribute, or null if the attribute does not exist
     */
    public <T> T getAttr(String name) {
        return (T) request.getAttribute(name);
    }

    /**
     * Returns the value of the named attribute as an Object, or null if no attribute of the given name exists.
     *
     * @param name a String specifying the name of the attribute
     * @return an String Object containing the value of the attribute, or null if the attribute does not exist
     */
    public String getAttrForStr(String name) {
        return (String) request.getAttribute(name);
    }

    /**
     * Returns the value of the named attribute as an Object, or null if no attribute of the given name exists.
     *
     * @param name a String specifying the name of the attribute
     * @return an Integer Object containing the value of the attribute, or null if the attribute does not exist
     */
    public Integer getAttrForInt(String name) {
        return (Integer) request.getAttribute(name);
    }

    /**
     * Returns the value of the specified request header as a String.
     */
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    private Integer toInt(String value, Integer defaultValue) {
        try {
            if (StringUtil.isBlank(value))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Integer.parseInt(value.substring(1));
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Integer value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Integer.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Integer getParaToInt(String name) {
        return toInt(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Integer with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Integer getParaToInt(String name, Integer defaultValue) {
        return toInt(request.getParameter(name), defaultValue);
    }

    private Long toLong(String value, Long defaultValue) {
        try {
            if (StringUtil.isBlank(value))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Long.parseLong(value.substring(1));
            return Long.parseLong(value);
        } catch (Exception e) {
            throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Long value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Long.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Long getParaToLong(String name) {
        return toLong(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Long with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Long getParaToLong(String name, Long defaultValue) {
        return toLong(request.getParameter(name), defaultValue);
    }

    private Boolean toBoolean(String value, Boolean defaultValue) {
        if (StringUtil.isBlank(value))
            return defaultValue;
        value = value.trim().toLowerCase();
        if ("1".equals(value) || "true".equals(value))
            return Boolean.TRUE;
        else if ("0".equals(value) || "false".equals(value))
            return Boolean.FALSE;
        throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Boolean value.");
    }

    /**
     * Returns the value of a request parameter and convert to Boolean.
     *
     * @param name a String specifying the name of the parameter
     * @return true if the value of the parameter is "true" or "1", false if it is "false" or "0", null if parameter is not exists
     */
    public Boolean getParaToBoolean(String name) {
        return toBoolean(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Boolean with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return true if the value of the parameter is "true" or "1", false if it is "false" or "0", default value if it is null
     */
    public Boolean getParaToBoolean(String name, Boolean defaultValue) {
        return toBoolean(request.getParameter(name), defaultValue);
    }


    private Date toDate(String value, Date defaultValue) {
        try {
            if (StringUtil.isBlank(value))
                return defaultValue;
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value.trim());
        } catch (Exception e) {
            throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Date value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Date.
     *
     * @param name a String specifying the name of the parameter
     * @return a Date representing the single value of the parameter
     */
    public Date getParaToDate(String name) {
        return toDate(request.getParameter(name), null);
    }


    /**
     * Return HttpSession.
     */
    public HttpSession getSession() {
        return request.getSession();
    }

    /**
     * Return HttpSession.
     *
     * @param create a boolean specifying create HttpSession if it not exists
     */
    public HttpSession getSession(boolean create) {
        return request.getSession(create);
    }

    /**
     * Return a Object from session.
     *
     * @param key a String specifying the key of the Object stored in session
     */
    public <T> T getSessionAttr(String key) {
        HttpSession session = request.getSession(false);
        return session != null ? (T) session.getAttribute(key) : null;
    }

    /**
     * Store Object to session.
     *
     * @param key   a String specifying the key of the Object stored in session
     * @param value a Object specifying the value stored in session
     */
    public WeChatMsgManager setSessionAttr(String key, Object value) {
        request.getSession(true).setAttribute(key, value);
        return this;
    }

    /**
     * Remove Object in session.
     *
     * @param key a String specifying the key of the Object stored in session
     */
    public WeChatMsgManager removeSessionAttr(String key) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.removeAttribute(key);
        return this;
    }

    /**
     * Get cookie value by cookie name.
     */
    public String getCookie(String name, String defaultValue) {
        Cookie cookie = getCookieObject(name);
        return cookie != null ? cookie.getValue() : defaultValue;
    }

    /**
     * Get cookie value by cookie name.
     */
    public String getCookie(String name) {
        return getCookie(name, null);
    }

    /**
     * Get cookie value by cookie name and convert to Integer.
     */
    public Integer getCookieToInt(String name) {
        String result = getCookie(name);
        return result != null ? Integer.parseInt(result) : null;
    }

    /**
     * Get cookie value by cookie name and convert to Integer.
     */
    public Integer getCookieToInt(String name, Integer defaultValue) {
        String result = getCookie(name);
        return result != null ? Integer.parseInt(result) : defaultValue;
    }

    /**
     * Get cookie value by cookie name and convert to Long.
     */
    public Long getCookieToLong(String name) {
        String result = getCookie(name);
        return result != null ? Long.parseLong(result) : null;
    }

    /**
     * Get cookie value by cookie name and convert to Long.
     */
    public Long getCookieToLong(String name, Long defaultValue) {
        String result = getCookie(name);
        return result != null ? Long.parseLong(result) : defaultValue;
    }

    /**
     * Get cookie object by cookie name.
     */
    public Cookie getCookieObject(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(name))
                    return cookie;
        return null;
    }

    /**
     * Get all cookie objects.
     */
    public Cookie[] getCookieObjects() {
        Cookie[] result = request.getCookies();
        return result != null ? result : new Cookie[0];
    }

    /**
     * Set Cookie.
     *
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     * @param isHttpOnly      true if this cookie is to be marked as HttpOnly, false otherwise
     */
    public WeChatMsgManager setCookie(String name, String value, int maxAgeInSeconds, boolean isHttpOnly) {
        return doSetCookie(name, value, maxAgeInSeconds, null, null, isHttpOnly);
    }

    /**
     * Set Cookie.
     *
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     */
    public WeChatMsgManager setCookie(String name, String value, int maxAgeInSeconds) {
        return doSetCookie(name, value, maxAgeInSeconds, null, null, null);
    }

    /**
     * Set Cookie to response.
     */
    public WeChatMsgManager setCookie(Cookie cookie) {
        response.addCookie(cookie);
        return this;
    }

    /**
     * Set Cookie to response.
     *
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     * @param path            see Cookie.setPath(String)
     * @param isHttpOnly      true if this cookie is to be marked as HttpOnly, false otherwise
     */
    public WeChatMsgManager setCookie(String name, String value, int maxAgeInSeconds, String path, boolean isHttpOnly) {
        return doSetCookie(name, value, maxAgeInSeconds, path, null, isHttpOnly);
    }

    /**
     * Set Cookie to response.
     *
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     * @param path            see Cookie.setPath(String)
     */
    public WeChatMsgManager setCookie(String name, String value, int maxAgeInSeconds, String path) {
        return doSetCookie(name, value, maxAgeInSeconds, path, null, null);
    }

    /**
     * Set Cookie to response.
     *
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     * @param path            see Cookie.setPath(String)
     * @param domain          the domain name within which this cookie is visible; form is according to RFC 2109
     * @param isHttpOnly      true if this cookie is to be marked as HttpOnly, false otherwise
     */
    public WeChatMsgManager setCookie(String name, String value, int maxAgeInSeconds, String path, String domain, boolean isHttpOnly) {
        return doSetCookie(name, value, maxAgeInSeconds, path, domain, isHttpOnly);
    }

    /**
     * Remove Cookie.
     */
    public WeChatMsgManager removeCookie(String name) {
        return doSetCookie(name, null, 0, null, null, null);
    }

    /**
     * Remove Cookie.
     */
    public WeChatMsgManager removeCookie(String name, String path) {
        return doSetCookie(name, null, 0, path, null, null);
    }

    /**
     * Remove Cookie.
     */
    public WeChatMsgManager removeCookie(String name, String path, String domain) {
        return doSetCookie(name, null, 0, path, domain, null);
    }

    private WeChatMsgManager doSetCookie(String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        // set the default path value to "/"
        if (path == null) {
            path = "/";
        }
        cookie.setPath(path);

        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (isHttpOnly != null) {
//            cookie.setHttpOnly(isHttpOnly);
        }
        response.addCookie(cookie);
        return this;
    }

    public void renderText(String text) {
        renderText(text, DEFAULT_CONTENT_TYPE);
    }

    public void renderText(String text, ContentType contentType) {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache");    // HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            response.setContentType(contentType.value());
            response.setCharacterEncoding("UTF-8");    // 与 contentType 分开设置

            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            LogKit.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public boolean isConfigServerRequest() {
        return StringUtil.notBlank(getPara("echostr"));
    }

    public void configServer() {
        // 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
        String echostr = getPara("echostr");
        String signature = getPara("signature");
        String timestamp = getPara("timestamp");
        String nonce = getPara("nonce");
        boolean isOk = SignatureCheckKit.me.checkSignature(signature, timestamp, nonce);
        if (isOk)
            renderText(echostr);

    }

    /**
     * 在接收到微信服务器的 InMsg 消息后后响应 OutMsg 消息
     *
     * @param outMsg 输出对象
     */
    public void render(OutMsg outMsg) {
        String outMsgXml = outMsg.toXml();

        // 是否需要加密消息
        if (Config.weChatApiConfig.isEncryptMessage()) {
            outMsgXml = MsgEncryptKit.encrypt(outMsgXml, getPara("timestamp"), getPara("nonce"));
        }

        // 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
        if (Config.devMode) {
            System.out.println("发送消息:");
            System.out.println(outMsgXml);
            System.out.println("--------------------------------------------------------------------------------\n");
        }

        renderText(outMsgXml, ContentType.XML);
    }

    public void renderOutTextMsg(String content) {
        OutTextMsg outMsg = new OutTextMsg(getInMsg());
        outMsg.setContent(content);
        render(outMsg);
    }

    public String getInMsgXml() {
        if (inMsgXml == null)
            inMsgXml = HttpKit.readData(request);

        // 是否需要解密消息
        if (Config.weChatApiConfig.isEncryptMessage()) {
            inMsgXml = MsgEncryptKit.decrypt(inMsgXml, getPara("timestamp"), getPara("nonce"), getPara("msg_signature"));
        }

        if (StringUtil.isBlank(inMsgXml)) {
            throw new RuntimeException("请不要在浏览器中请求该连接,调试请查看WIKI:http://git.oschina.net/jfinal/jfinal-weixin/wikis/JFinal-weixin-demo%E5%92%8C%E8%B0%83%E8%AF%95");
        }
        return inMsgXml;
    }

    public InMsg getInMsg() {
        if (inMsg == null)
            inMsgXml = getInMsgXml();
        if (Config.devMode)
            System.out.println("接收消息:" + inMsgXml);
        return InMsgParser.parse(inMsgXml);
    }
}
