package com.wechat.wwt.weixin.api;


/**
 * 将 WeChatApiConfig 绑定到 ThreadLocal 的工具类，以方便在当前线程的各个地方获取 WeChatApiConfig 对象：
 * 1：如果控制器继承自 MsgController 该过程是自动的，详细可查看 MsgInterceptor 与之的配合
 * 2：如果控制器继承自 ApiController 该过程是自动的，详细可查看 ApiInterceptor 与之的配合
 * 3：如果控制器没有继承自 MsgController、ApiController，则需要先手动调用 
 *    ApiConfigKit.setThreadLocalApiConfig(WeChatApiConfig) 来绑定 apiConfig 到线程之上
 */
public class ApiConfigKit {
	
	private static WeChatApiConfig apiConfig = null;
	
	// 开发模式将输出消息交互 xml 到控制台
	private static boolean devMode = false;
	
	public static void setDevMode(boolean devMode) {
		ApiConfigKit.devMode = devMode;
	}
	
	public static boolean isDevMode() {
		return devMode;
	}
	
	public static void config(WeChatApiConfig apiConfig){
		ApiConfigKit.apiConfig = apiConfig;
	}
	
	public static WeChatApiConfig getApiConfig() {
		if (apiConfig == null)
			throw new IllegalStateException("please init ApiConfigKit.config(apiConfig) first");
		return apiConfig;
	}

}