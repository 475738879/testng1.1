package com.hxk.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.methodpackage.basic.testbasic;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CourseRecord404 extends testbasic {

    @Test
    public void wo404() throws Exception{
//        login login = new login();
//        String token = login.login();
        ReadToken readToken = new ReadToken();
        String token = readToken.readTxt();

        HttpGet httpGet=new HttpGet("https://api-test.liupinshuyuan.com/lptService/api/giving/course/record?userPhone=&userName=&curPage=1&pageSize=10");
        httpGet.addHeader("Authorization",token);
        // 通过HttpClient来执行请求，获取一个响应结果
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 添加断言其一，获取服务器响应的状态码
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .build();
        httpGet.setConfig(requestConfig);

        // 获取Response Headers的值
        Header[] headerArray = response.getAllHeaders(); //获取响应头信息,返回是一个数组
        HashMap<String, String> hm = new HashMap<String, String>(); //创建一个hashmap对象
        // 增强for循环遍历headerArray数组，依次把元素添加到hashmap集合
        for(Header header : headerArray) {
            hm.put(header.getName(), header.getValue());
        }
        // 获取Content-Type的类型
        HttpEntity httpentity = response.getEntity();
        // 获取Response Body结果
        String str = EntityUtils.toString(httpentity, "utf-8");
        System.out.println("courseRecord接口的Response Body结果为：" + str);

        JSONObject json = JSON.parseObject(str); //将str的结果转换成json格式
        Object code = json.get("status");
        System.out.println("状态码:"+code);
        // 添加断言其二，获取服务器响应的状态码
        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "服务器返回的状态码不是200");
        System.out.println("服务器响应的状态码为：" + statusCode);
    }
}
