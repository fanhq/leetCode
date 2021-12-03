package com.fanhq.example;


import com.fanhq.example.util.SignatureUtils;

import java.net.URLEncoder;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {
    public static void main(String[] args) throws Exception {

        // 方法一
        System.out.println("方法一：");
        String str = "GET&%2F&AccessKeyId%3D" + "KVRn5re7nBWlG9okcBae8Eiorkseej"
                + "%26Action%3DRegisterDevice%26DeviceName%3D1533023037%26Format%3DJSON%26ProductKey%3DaxxxUtgaRLB%26RegionId%3Dcn-shanghai%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3D1533023037%26SignatureVersion%3D1.0%26Timestamp%3D2018-07-31T07%253A43%253A57Z%26Version%3D2018-01-20";
        byte[] signBytes;
        try {
            signBytes = SignatureUtils.hmacSHA1Signature("KVRn5re7nBWlG9okcBae8Eiorkseej" + "&", str.toString());
            String signature = SignatureUtils.newStringByBase64(signBytes);
            System.out.println("signString---" + str);
            System.out.println("signature----" + signature);
            System.out.println("最终signature：" + URLEncoder.encode(signature, "utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}