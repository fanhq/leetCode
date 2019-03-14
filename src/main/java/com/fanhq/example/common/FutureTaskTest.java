package com.fanhq.example.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by yileilei on 18/1/8.
 */
public class FutureTaskTest {

    ExecutorService executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                                    new ArrayBlockingQueue<Runnable>(10));

    public void test(){

        for (int j=0;j<50;j++){
            new Thread(new Runnable(){

                public void run() {
                    for (int i=0;;i++){
                        //System.out.println("request " + i);
                        request();
                    }

                }
            }).start();
        }


        System.out.println("over");
    }


    public void request(){
        FutureTask<String> future =
                new FutureTask<String>(new Callable<String>() {//使用Callable接口作为构造参数
                    public String call() {
                        //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
                        String result ="";
                        try {
                            result = sendGet("http://blog.csdn.net/iijse/article/details/6201101","");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return result;
                    }});

        //在这里可以做别的任何事情
        try {
            executor.execute(future);
            String result = future.get(1000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
            System.out.println(result);
        }catch (RejectedExecutionException e){
            //System.out.println("reject");
        }catch (InterruptedException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
        }

//        finally {
//            executor.shutdown();
//        }
    }

    public String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + param;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "/n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
