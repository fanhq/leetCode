package com.leetCode.conmon;

import java.io.*;

/**
 * Created by Hachel on 2018/1/9
 */
public class JavaIO {

    private static final String path = "D:\\subline text\\sql";

    public static void main(String[] args) {
        try {
            File file = new File(path);
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
