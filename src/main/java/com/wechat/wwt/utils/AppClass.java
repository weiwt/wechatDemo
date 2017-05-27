package com.wechat.wwt.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * @author wwt
 * @date 2017/5/17 10:54
 */
public class AppClass {

    public static void main(String[] args) throws Exception {
        test1();
//        test2();
    }

    public static void test1() throws Exception {
        String url = "https://www.qcloud.com/act/campus/ajax/index?uin=100000658836&csrfCode=813332720&reqSeqId=b2816a89-5b38-c9ba-7520-622b0eb12081&action=getVoucher";

        String cookie = "qcloud_uid=81af755ae8b6c94ba7accf39e353c0ca; UM_distinctid=15c0a403f431b7-0d53055cf4d7f2-323f5c0f-1fa400-15c0a403f44326; deviceType=cvm; regionId=1; expiredType=2; pgv_pvi=1328698368; language=zh; qcloud_visitId=1c67bf6d7ceb12efd8cf1fdef02b7a9f; qcloud_from=gwzcw.5677.5677.5677-1495417762062; pgv_si=s4625793024; lastLoginType=email; lusername=1327127023%40qq.com; uin=o100000658836; tinyid=144115200533749072; skey=ewCi7RdTVgDyh6yDzzvsHseM1E5lde6Paz7kv1as3FM_; nick=1327127023%40qq.com; vcode_sig=h016b72c943304fa054803dff5b24d91c7c8d65fa7184d07ad20bad23cc7a0e81607fd3355e5c0b1ff1; _gat=1; qcmain.sid=s%3AFIylXSRXg4VfJbjSHcpATyQqoQiwe4PI.VveL%2B9C5FFME%2BCD6gkWucAW1jY3e0TxzXjoCtrpRUK4; qcact.sid=s%3A89YRNEiQ-4T_7HANsnFTNvUwnlBh4GuD.v863oPs8IWNfEMERYIFFgtNHAjQA1mB0mg4mS1qsZZw; _ga=GA1.2.363390224.1487061628; intl=1";
        //使用httpClient后台发起查询
        HttpClient httpClient = new HttpClient();

        PostMethod getMethod = new PostMethod(url);
        getMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        getMethod.addRequestHeader("Cookie", cookie);
        //发起请求
        while (true){
            int statusCode = httpClient.executeMethod(getMethod);
            String callBack = null;
            if (statusCode == HttpStatus.SC_OK) {
                callBack = getMethod.getResponseBodyAsString();
            }
            System.out.println(callBack);
        }
    }

    public static void test2() throws Exception {
        String requestUrl = "https://www.qcloud.com/act/campus/ajax/index?uin=100000658836&csrfCode=2055740477&reqSeqId=2d37c5f0-cf69-810b-a346-14ac7a04c6b6&action=getVoucher";
        String requestMethod = "GET";
        String cookie = "qcloud_from=gwzcw.5677.5677.5677-1494820441984; qcloud_uid=81af755ae8b6c94ba7accf39e353c0ca; UM_distinctid=15c0a403f431b7-0d53055cf4d7f2-323f5c0f-1fa400-15c0a403f44326; deviceType=cvm; regionId=1; expiredType=2; pgv_pvi=1328698368; language=zh; qcloud_visitId=78c18b61aae6f6c02685c37d1b2267a4; _gat=1; pgv_si=s6057412608; lastLoginType=email; lusername=1327127023%40qq.com; uin=o100000658836; tinyid=144115200533749072; skey=t1sAqxmx*i4MBv4k6zV3OxS0YeFEouuyS03MjX-3BAA_; qcmain.sid=s%3AhomiAT1DIaQTmGrFaPhN4Dz1cXDWDk3I.47axiV%2FCZ7kB9mfrne2kXX%2BTdlHnf3D9qQMYckQXUVY; nick=1327127023%40qq.com; qcact.sid=s%3A9I2pjLedpmTaf3G3tIolbHdDFQXsPEP-.9vxvLovSPzhK%2B35mU6XGLUs60eB4Q41t%2BXCOIqo%2BiRU; _ga=GA1.2.363390224.1487061628; intl=1";
        String outputStr = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            httpUrlConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            httpUrlConn.setRequestProperty("Connection", "keep-alive");
            httpUrlConn.setRequestProperty("Content-Length", "17");
            httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpUrlConn.setRequestProperty("Cookie", cookie);
            httpUrlConn.setRequestProperty("Host", "www.qcloud.com");
            httpUrlConn.setRequestProperty("Origin", "https://www.qcloud.com");
            httpUrlConn.setRequestProperty("Referer", "https://www.qcloud.com/act/campus");
            httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            httpUrlConn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            System.out.println(buffer.toString());
        } catch (ConnectException ce) {
//            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
//            log.error("https request error:{}", e);
        }
    }
}
