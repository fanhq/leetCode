package com.leetCode.guava;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
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
            Strings.isNullOrEmpty("");
            Strings.nullToEmpty("");
            Strings.emptyToNull("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 将文件内容一行一行读取出来
            File file = new File("");
            List<String> readLines = Files.readLines(file, Charsets.UTF_8);

            FileUtils.forceDelete(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
