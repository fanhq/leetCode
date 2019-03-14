package com.fanhq.example.problems;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by Hachel on 2018/1/2
 */
public class SortLogin {

    private static final Logger logger = LoggerFactory.getLogger(SortLogin.class);


    public static void main(String[] args) {

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
