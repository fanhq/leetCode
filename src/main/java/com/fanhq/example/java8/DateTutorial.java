package com.fanhq.example.java8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author fanhaiqiu
 * @date 2019/4/15
 */
public class DateTutorial {

    public static void main(String[] args) {
        //clock
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        System.out.println(millis);
        System.out.println(System.currentTimeMillis());
        Instant instant = clock.instant();
        System.out.println(instant);
        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);
        //timezone
        System.out.println(ZoneId.getAvailableZoneIds());
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());
        //localdate
        LocalDate today = LocalDate.now();
        System.out.println("今天的日期: " + today);
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        System.out.println("明天的日期: " + tomorrow);
        LocalDate yesterday = tomorrow.minusDays(2);
        System.out.println("昨天的日期: " + yesterday);
        LocalDate independenceDay = LocalDate.of(2019, Month.MARCH, 12);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println("今天是周几:" + dayOfWeek);
        String str1 = "2014==04==12 01时06分09秒";
        // 根据需要解析的日期、时间字符串定义解析所用的格式器
        DateTimeFormatter fomatter1 = DateTimeFormatter.ofPattern("yyyy==MM==dd HH时mm分ss秒");
        LocalDateTime dt1 = LocalDateTime.parse(str1, fomatter1);
        System.out.println(dt1);
        LocalDateTime rightNow = LocalDateTime.now();
        String date = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(rightNow);
        System.out.println(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatter.format(rightNow));
        //LocalDate转Date
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date datetime = Date.from(zdt.toInstant());
        System.out.println("LocalDate = " + localDate);
        System.out.println("Date = " + datetime);
        //Date转LocalDate
        Date date1 = new Date();
        Instant instant1 = date1.toInstant();
        ZoneId zoneId1 = ZoneId.systemDefault();
        LocalDate localDate1 = instant1.atZone(zoneId1).toLocalDate();
        System.out.println("Date = " + date1);
        System.out.println("LocalDate = " + localDate1);
    }

}
