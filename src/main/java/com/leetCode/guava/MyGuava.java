package com.leetCode.guava;

import java.io.File;

/**
 * Created by Hachel on 2018/1/23
 */
public class MyGuava {


    public static void main(String[] args) {
        try {
            File file =  new File("D:\\temp file\\测试\\dddd");
            //FileUtils.forceMkdir(file);
            System.out.println(file.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Strings.isNullOrEmpty("");
//            Strings.nullToEmpty("");
//            Strings.emptyToNull("");
//            // 将文件内容一行一行读取出来
//            File file = new File("");
//            List<String> readLines = Files.readLines(file, Charsets.UTF_8);
//
//            FileUtils.forceDelete(file);
//
//            FileUtils.forceMkdir(new File("D:\\temp file\\测试"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
