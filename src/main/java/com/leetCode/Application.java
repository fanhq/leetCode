package com.leetCode;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {


    public static void main(String[] args) {
        try {
           // FileUtils.forceMkdir(new File("D:\\temp file\\测试.txt"));
            FileUtils.write(new File("D:\\temp file\\s\\测试.txt"), "data");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
