package com.methodpackage.restclient;

import java.io.IOException;
import com.methodpackage.basic.testbasic;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.Header;
import org.testng.Assert;
import java.util.HashMap;


public class restclient extends testbasic {

    // Get请求
    public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {

        // 创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);

        // 执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .build();
        httpget.setConfig(requestConfig);

        // 添加断言，获取服务器响应的状态码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "服务器返回的状态码不是200");
        System.out.println("服务器响应的状态码为：" + statusCode);

        // 获取响应头信息,返回是一个数组
        Header[] headerArray = httpResponse.getAllHeaders();

        // 创建一个hashmap对象，通过postman可以看到请求响应头信息都是Key和value得形式
        HashMap<String, String> hm = new HashMap<String, String>();

        // 增强for循环遍历headerArray数组，依次把元素添加到hashmap集合
        for(Header header : headerArray) {
            hm.put(header.getName(), header.getValue());
        }

        // 打印hashmap
        System.out.println("Response Headers的结果为："+ hm);

        // 关闭
        return httpResponse;
    }
}