package com.leetCode.common;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Hachel on 2018/3/1
 */
public class JavaNIO {
    public static void main(String[] args) {

        File file = new File("D:\\temp file\\privateKey.keystore");
        try {
            //
            FileInputStream fis = new FileInputStream(file);
            FileChannel fileChannel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            int length = -1;
            while ((length = fileChannel.read(buffer)) != -1) {
                buffer.clear();
                byte[] data = buffer.array();
                System.out.println(new String(data));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
