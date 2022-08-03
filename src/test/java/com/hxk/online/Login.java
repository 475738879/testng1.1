package com.hxk.online;

import com.alibaba.fastjson.JSON;
import com.methodpackage.basic.testbasic;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.testng.Assert;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author    Anker
 * @date      2021/07/22
 * @analysis  login用例是登录接口——>(POST请求：带Body参数，不带Params参数，不带Headers参数，application/json格式)
 */

public class Login extends testbasic {

    public String token;  //定义一个变量，用于存放登录后的token值

    // 登录接口测试并获取token值
    public String login() throws Exception {

        // 通过HttpPost来发送post请求，带Body参数
        HttpPost httpPost = new HttpPost("https://api-test.liupinshuyuan.com/userCenter/api/v1/login/backLogin");
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userAccount", "13429667373");
        jsonParam.put("password", "he475738879123");
        jsonParam.put("platId",4);
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
        // 打印hashmap
//        System.out.println("Response Headers的结果为："+ hm);

        // 获取Content-Type的类型
        HttpEntity httpentity = response.getEntity();

        // 获取Response Body结果
        String str = EntityUtils.toString(httpentity, "utf-8");
        writeTxt(str.toString()); //调用writeTxt()函数，将Response Body的结果保存到本地文本文件中
        System.out.println("登录接口的Response Body结果为：" + str);

        // 登录成功后，获取服务器返回Body值里的token值
        JSONObject json = JSON.parseObject(str); //将str的结果转换成json格式
        JSONObject data = json.getJSONObject("data");
        token = data.getJSONObject("tokenInfo").getString("refreshToken");
        System.out.println("login登录成功后的token值为：" + token);

        return token;
        // 关闭
//        response.close();
    }

    // 将服务器的响应结果保存到本地文本文件中
    public static void  writeTxt(String str) throws Exception{

        File file=new File("./Api_AutoTestCase");
        if(!file.exists()){ //如果文件夹不存在
            file.mkdir(); //创建文件夹
        }
        FileWriter fw = null;
        String path = "./Api_AutoTestCase/token.txt";
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(str.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}