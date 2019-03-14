package com.fanhq.example.guava;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Hachel on 2018/1/23
 */
public class MyGuava {


    public static void main(String[] args) {
        try {
            File file =  new File("D:\\temp file\\测试\\dddd");
            FileUtils.forceMkdir(file);
            System.out.println(file.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Strings.isNullOrEmpty("");
            Strings.nullToEmpty("");
            Strings.emptyToNull("");
            // 将文件内容一行一行读取出来
            File file = new File("");
            List<String> readLines = Files.readLines(file, Charsets.UTF_8);

            FileUtils.forceDelete(file);

            //限流，每秒两次请求
            RateLimiter limiter = RateLimiter.create(2);
            limiter.tryAcquire();

            FileUtils.forceMkdir(new File("D:\\temp file\\测试"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
