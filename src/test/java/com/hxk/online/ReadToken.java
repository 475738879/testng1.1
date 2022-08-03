package com.hxk.online;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadToken {
    /**传入txt路径读取txt文件
     * @param
     * @return 返回读取到的内容
     */

    public static String readTxt() {
        String token;  //定义一个变量，用于存放登录后的token值
        File file = new File("./Api_AutoTestCase/token.txt");
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer sb=new StringBuffer();
                String test = null;
                while ((test = bufferedReader.readLine())!=null){
                    JSONObject json = JSON.parseObject(test); //将str的结果转换成json格式
                    JSONObject data = json.getJSONObject("data");
                    token = data.getJSONObject("tokenInfo").getString("refreshToken");
                    sb.append(token);
                }
                return sb.toString();
                } catch (Exception e){
                    e.printStackTrace();
            }
            }
        return null;


}
    }