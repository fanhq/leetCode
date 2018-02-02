package com.leetCode;


import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {


    public static void main(String[] args) {
        try {
            InputStream is = Application.class.getClassLoader().getResourceAsStream("application.properties");
            Properties properties = new Properties();
            properties.load(is);
            System.out.println(properties.getProperty("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
