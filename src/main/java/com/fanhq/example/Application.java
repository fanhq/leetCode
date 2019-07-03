package com.fanhq.example;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanhq.example.model.Test001;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    public static void main(String[] args) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "fanhaiqui");
        jsonObject.put("fiile", "ss");
        Test001 test001 = JSON.parseObject(jsonObject.toJSONString(), Test001.class);
        System.out.println(test001);

    }


}