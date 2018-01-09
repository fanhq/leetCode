package com.leetCode.problems;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2017/12/26
 */
public class HttpClientMgr {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientMgr.class);

    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager connMgr;

    /**
     * 请求超时设置
     */
    private static RequestConfig requestConfig;

    static {
        try {
            // 设置连接池
            connMgr = new PoolingHttpClientConnectionManager();
            // 设置连接池大小
            connMgr.setMaxTotal(256);
            // 单路由最大连接数
            connMgr.setDefaultMaxPerRoute(256);
            //官方推荐使用这个来检查永久链接的可用性，而不推荐每次请求的时候才去检查
            connMgr.setValidateAfterInactivity(2000);
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(1500);
            // 设置读取超时
            configBuilder.setSocketTimeout(3000);
            // 设置从连接池获取连接实例的超时，不能太大，不要让大量请求堆积在获取连接处
            configBuilder.setConnectionRequestTimeout(500);
            //不重定向连接
            configBuilder.setRedirectsEnabled(false);
            requestConfig = configBuilder.build();
        } catch (Exception e) {
            logger.error("创建https连接失败。", e);
        }
    }

    /**
     * 获取httpclient 连接池方式
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connMgr)
                .build();
        return httpClient;
    }

    /**
     * 获取httpclient 不采用连接池方式
     *
     * @return
     */
    public static CloseableHttpClient getDefaultClient() {
        return HttpClients.createDefault();
    }

    /**
     * @return
     */
    public static RequestConfig getDefaultRequestConfig() {
        return requestConfig;
    }

    /**
     * 关闭连接或者连接池
     */
    public static void closeConnection(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        try {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
            if (httpClient != null){
                httpClient.close();
            }
        } catch (Exception e) {
            logger.error("EntityUtils.consume(response.getEntity()) failed!", e);
        }
    }

    /**
     * 释放连接
     */
    public static void releaseConnection(HttpRequestBase httpMethod){
        try {
            if (httpMethod != null){
                httpMethod.releaseConnection();
            }
        } catch (Exception e) {
            logger.error("releaseConnection failed!", e);
        }
    }


    /**
     * 连接回收策略
     */
    public static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(10000);
                        // 关闭失效的连接
                        connMgr.closeExpiredConnections();
                        // 可选的, 关闭30秒内不活动的连接
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                logger.error("连接回收失败", ex);
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }

}
