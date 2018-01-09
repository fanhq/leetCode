package com.leetCode.problems;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * Created by Hachel on 2017/12/26
 */
public class HttpClientMgr {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientMgr.class);

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

}
