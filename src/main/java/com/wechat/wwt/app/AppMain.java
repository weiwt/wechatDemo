package com.wechat.wwt.app;

import com.wechat.wwt.utils.HttpUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class AppMain {

    public static void main(String[] args){
        String url = "";
        String cookie = "";



    }


    public static void test1(String url ,String cookie) {
        try{

            //使用httpClient后台发起查询
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter("Content-Encoding", HttpUtil.UTF_8);
            httpClient.getParams().setParameter("; charset=", HttpUtil.UTF_8);
            httpClient.getParams().setParameter("US-ASCII", HttpUtil.UTF_8);
            //设置请求url及请求报文
            GetMethod getMethod = new GetMethod(url);

//            getMethod.setRequestHeader("");
//            getMethod.setRequestHeader();
            //发起请求
            int statusCode = httpClient.executeMethod(getMethod);
            String callBack = null;
            if (statusCode == HttpStatus.SC_OK) {
                callBack = getMethod.getResponseBodyAsString();
            }
            System.out.println(callBack);
        }catch (Exception e){
        }

    }
}
