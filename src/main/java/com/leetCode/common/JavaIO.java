package com.leetCode.common;

import com.leetCode.Application;

import java.io.*;
import java.util.Properties;

/**
 * Created by Hachel on 2018/1/9
 */
public class JavaIO {

    private static final String path = "D:\\subline text\\sql";

    public static void main(String[] args) {

        try {
            //写文件
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(""), true), "GBK"));
            osw.write("");
            osw.close();
            //默认加载resource下面找
            InputStream is = Application.class.getClassLoader().getResourceAsStream("application.properties");
            Properties properties = new Properties();
            properties.load(is);
            System.out.println(properties.getProperty("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File(path);
            file.length();
            //1、用于持续输出流的写法，不会阻塞
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
            }
            bos.close();
            byte[] data = bos.toByteArray();
            System.out.println(new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
