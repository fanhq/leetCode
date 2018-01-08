package com.leetCode.problems;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by Hachel on 2018/1/2
 */
public class SortLogin {

    private static final Logger logger = LoggerFactory.getLogger(SortLogin.class);

    private static final String path ="D:\\subline text\\sql";

    public static void main(String[] args) {

        File file = new File(path);
        try {
            StringBuilder result1 = new StringBuilder();
            //1
            byte[] temp = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(file);
            while (fileInputStream.read(temp) != -1){
                result1.append(new String(temp));
            }
            System.out.println(result1);
            //2
            StringBuilder result2 = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                result2.append(line);
            }
            System.out.println(result2);
        }catch (Exception e){
            logger.error("", e);
        }

        TreeSet<SortString> treeSets = new TreeSet<SortString>(new Comparator<SortString>() {
            public int compare(SortString o1, SortString o2) {
                if (o1.getCount() < o2.getCount()){
                    return -1;
                }else if (o1.getCount() > o2.getCount()){
                    return 1;
                }else {
                    return 0;
                }
            }
        });
    }


    private static class SortString{

        private String key ;
        private Integer count ;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "SortString{" +
                    "key='" + key + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

}
