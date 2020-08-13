package com.fanhq.example.cache;

import java.io.File;

/**
 * Created by Hachel on 2018/1/23
 */
public class MyGuava {


    public static void main(String[] args) {
        try {
            File file =  new File("D:\\temp file\\data\\user_20200103.data");
//            file.createNewFile();
//            Files.write("a".getBytes(),file);
//            Files.write("b".getBytes(),file);
//            List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
//            CharSink sink = Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND);
//            sink.writeLines(names, System.lineSeparator());
//            File file =  new File("D:\\temp file\\a.txt");
           // Files.createParentDirs(file);
            //System.out.println(file.exists());
            //file.mkdirs();
            file.delete();
            file.createNewFile();
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
//            //限流，每秒两次请求
//            RateLimiter limiter = RateLimiter.create(2);
//            limiter.tryAcquire();
//
//            FileUtils.forceMkdir(new File("D:\\temp file\\测试"));
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
