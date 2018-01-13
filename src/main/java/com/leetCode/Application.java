package com.leetCode;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String time = sdf.format(cal.getTime());
        System.out.println(time);
        System.out.println(cal.getTimeInMillis());
        System.out.println(System.currentTimeMillis());
    }

}
