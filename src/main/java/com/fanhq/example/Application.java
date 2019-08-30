package com.fanhq.example;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {

    public static void main(String[] args) throws Exception{
        String key = "34757_hw_34757";
        String value = "{\"type\":21,\"data\":{\"task_id\":\"Py4dNdqhUO\",\"result\":{\"code\":\"OK\",\"message\":\"OK\",\"data\":{\"1\":false}},\"origin_type\":\"HEWU\"}}";
        JSONObject o = JSON.parseObject(value);
        System.out.println(o.getString("type"));
        JSONObject d = o.getJSONObject("data");
        System.out.println(d.getString("origin_type"));
        JSONObject result = d.getJSONObject("result");
        JSONObject data = result.getJSONObject("data");
        System.out.println(data);
        List<String> keyList = Arrays.asList(StringUtils.split(key, "_"));
        String oneNetId = keyList.get(0);
        String sn = keyList.get(1);
        System.out.println(oneNetId);
        System.out.println(sn);

        System.out.println(key.indexOf("_"));
        int index = key.indexOf("_");
        System.out.println(key.substring(0,index));
        System.out.println(key.substring(index+1));
    }


}