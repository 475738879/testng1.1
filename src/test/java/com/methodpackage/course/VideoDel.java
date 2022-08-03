package com.methodpackage.course;

import com.alibaba.fastjson.JSONObject;
import com.hxk.online.ReadToken;
import com.methodpackage.basic.testbasic;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoDel extends testbasic {

    public void videoDel(String id)throws Exception{
        ReadToken readToken = new ReadToken();
        String token = readToken.readTxt();
        // 通过HttpPost来发送post请求，带Body参数
        HttpPost httpPost = new HttpPost("https://api-test.liupinshuyuan.com/curricula/course/tcvideo/videoDel");
        httpPost.addHeader("Authorization",token);
        ArrayList arrayList = new ArrayList();
        JSONObject jsonParam = new JSONObject();
        arrayList.add(0,id);
        jsonParam.put("id", arrayList);
        //添加headers内容
        httpPost.addHeader("system","eduOnline");

        StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
        entity.setContentEncoding("UTF-8");

        // Headers的Content-Type类型
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        //打印httpPost
        System.out.println("请求是"+jsonParam);

        // 通过HttpClient来执行请求，获取一个响应结果
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 添加断言，获取服务器响应的状态码
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "服务器返回的状态码不是200");
        System.out.println("服务器响应的状态码为：" + statusCode);

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .build();
        httpPost.setConfig(requestConfig);

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
//        System.out.println("index接口的Response Body结果为：" + str);
        // 添加断言其二，获取服务器响应的状态码
//        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "服务器返回的状态码不是200");
//        System.out.println("服务器响应的状态码为：" + statusCode);
    }
}
