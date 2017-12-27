package com.leetCode.problems;

import com.alibaba.fastjson.JSON;
import com.asiainfo.openplatform.common.util.MD5Util;
import com.asiainfo.openplatform.common.util.RSAUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Hachel on 2017/12/26
 */
public class HttpClientTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    public static final SimpleDateFormat DATA_FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

    static {
        try {
            // 设置连接池
            connMgr = new PoolingHttpClientConnectionManager();
            // 设置连接池大小
            connMgr.setMaxTotal(128);
            connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
            // 在提交请求之前 测试连接是否可用(setStaleConnectionCheckEnabled过期方法已被替换)
            connMgr.setValidateAfterInactivity(300);
        } catch (Exception e) {
            logger.error("创建https连接失败。", e);
        }
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(1500);
        // 设置读取超时
        configBuilder.setSocketTimeout(3000);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(2000);

        requestConfig = configBuilder.build();
    }


    public static void main(String[] args) {
        ynOfferOrderCheck("13087411361", "1000039267");
        ynOfferOrderCheck("13308825070","1000039267");
    }

    /**
     * 云南实时接口查询互斥关系
     * @return
     */
    @SuppressWarnings("unchecked")
    private static boolean ynOfferOrderCheck(String telNo, String offerId) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        try {
            String host = "http://10.174.59.103:6666/oppf" + "?";
            String appKey =  "b061fae0b9b72a0accb4a61624d7cbf7";
            String appId =  "507059";
            String rsaPublicKey = "MGcwDQYJKoZIhvcNAQEBBQADVgAwUwJMAIT/IeHySAK3pjddw5WUR8tyCozjuW2Smi8JuONyFnTaeZxHQa7Ruiwz+M0IzHxQmBj0R1N5/aFs9GBaivuWWuHnZYL6QMq8iRn8cwIDAQAB";
            Map<String, Object> contentMap = new HashMap();
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            contentMap.put("ACCESS_NUM", telNo);
            contentMap.put("OFFER_ID", offerId);
            contentMap.put("ACTION", "0");
            contentMap.put("CHECK_MODE", "1");
            contentMap.put("SUBMIT_TYPE", "5");
            paramsMap.put("method", "YUNN_UNHQ_offerOrderCheck");
            paramsMap.put("appId", appId);
            paramsMap.put("timestamp", DATA_FORMAT_YYYYMMDDHHMMSS.format(new Date()));
            paramsMap.put("flowId", UUID.randomUUID().toString());
            paramsMap.put("channelTypeId", "1");
            paramsMap.put("tradeEparchyCode", "ZZZZ");
            paramsMap.put("tradeCityCode", "ZZZZ");
            paramsMap.put("tradeDepartId", "89603");
            paramsMap.put("tradeStaffId", "ITFBK053");
            paramsMap.put("appKey", appKey);
            String content = JSON.toJSONString(contentMap);
            String urlFmt = "method=%s&format=json&appId=%s&appKey=%s&status=1&timestamp=%s&provinceCode=YUNN&tradeStaffId=%s"
                    + "&tradeEparchyCode=%s&tradeCityCode=%s&tradeDepartId=%s&channelTypeId=%s&flowId=%s";

            String signFmt = urlFmt + "&content=%s";
            String urlStr = String.format(urlFmt, paramsMap.get("method"), paramsMap.get("appId"), paramsMap.get("appKey"),
                    paramsMap.get("timestamp"), paramsMap.get("tradeStaffId"), paramsMap.get("tradeEparchyCode"),
                    paramsMap.get("tradeCityCode"), paramsMap.get("tradeDepartId"), paramsMap.get("channelTypeId"), paramsMap.get("flowId"));

            // 构造调用报文 公共参数+业务参数(content)
            // 首字母排序 foo=1,bar=2,baz=3 构造之后是 231(取value值)
            String md5Sign = String.format(urlStr + "&content=%s", content);
            String[] params = md5Sign.split("&");
            Map<String, String> rsaMap = new HashMap<String, String>();
            for (String s : params) {
                String[] arr = s.split("=");
                if (arr.length == 2) {
                    rsaMap.put(arr[0], arr[1].trim()); // 构造map
                }
            }
            String[] keys = rsaMap.keySet().toArray(new String[rsaMap.size()]);

            Arrays.sort(keys); // 对key进行排序
            StringBuilder buf = new StringBuilder(200); // 定义首字母排序后的串 value
            for (String key : keys) {
                buf.append((String) rsaMap.get(key)); // 排序后的串value
            }
            String md5Later = "";// 定义MD5加密后的结果串
            if (buf.toString().length() > 0) {
                md5Later = MD5Util.MD5(buf.toString()); // 使用标准MD5加密API
            }

            String rsaSign = URLEncoder.encode(RSAUtils.encryptByPublicKey(md5Later, rsaPublicKey), "UTF-8");
            // 原始调用报文+数字签名sign 构造成公共参数
            String httpUrl = host + urlStr + "&sign=" + rsaSign;
            httpPost = new HttpPost(httpUrl);
            httpPost.setConfig(requestConfig);

            StringEntity stringEntity = new StringEntity(content);// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/" + signFmt);
            long start = System.currentTimeMillis();
            httpPost.setEntity(stringEntity);

            logger.info("begin request :http_url :" + httpUrl + " content :" + content + " bigin time(ms): " + start);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("url=【{}】 response http code is {}", httpUrl, statusCode);
            HttpEntity entity = response.getEntity();
            String httpStr = EntityUtils.toString(entity, "UTF-8");
            long end = System.currentTimeMillis();
            logger.info("CRM手机号码{}与产品{}的互斥关系检查耗时{}ms", telNo, offerId, (end - start));
            logger.info("check mutex response:" + httpStr);
        } catch (Exception e) {
            logger.error("云南查询互斥产品失败", e);
        }finally {
            if (httpPost != null){
                httpPost.releaseConnection();
            }
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("EntityUtils.consume(response.getEntity()) failed!", e);
                }
            }
        }
        return true;
    }

}
