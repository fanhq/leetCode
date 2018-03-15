package com.leetCode;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hachel on 2018/1/2
 */
public class Application {


    public static void main(String[] args) {
        try {
//            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
//            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("11111");
//                }
//            }, 0, 1, TimeUnit.SECONDS);
//
//            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("22222");
//                }
//            }, 0, 1, TimeUnit.SECONDS);

//            char a = 1;
//            String b = "a" + a + "b";
//            String ss = String.valueOf(a);
//            String [] c = b.split(String.valueOf(a));
//            for (String s : c) {
//                System.out.println(s);
//            }
//
////            Calendar calendar = Calendar.getInstance();
////            calendar.add(Calendar.DATE, -10);
////            System.out.println(System.currentTimeMillis());
////            System.out.println(calendar.getTimeInMillis());
//            byte[] a = Base64.decodeBase64("yJFRsJ5oQ2s=");
//            System.out.println(a.length);
//            for (byte b : a) {
//                System.out.print(b);
//            }
//            System.out.println();
//            byte[] b = String.valueOf(a).getBytes();
//            System.out.println(b.length);
//            for (byte b1 : b) {
//                System.out.print(b1);
//            }
//
//            RedisTemplate<String, String> stringRedisTemplate = RedisTemplateUtil.newInstance(String.class);
//            SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
//           // setOperations.add("aaaaa", "1111");
//            setOperations.remove("aaaaa", "1111");
//            Set<String> set = setOperations.members("aaaaa");
//            for (String s : set) {
//                System.out.println(s);
//            }
//            File file = new File("D:/temp file/bb.txt");
//            System.out.println(file.getAbsolutePath());
//            System.out.println(file.isFile());
//            System.out.println(file.isDirectory());

            String a = "D:/temp file/20180313/dna/bb.txt";
            System.out.println( a.replace("dna", "temp/dna"));
            System.out.println(a.contains("/dna/"));

            Map<String, String> h = new HashMap<>();
            System.out.println(h.get("d"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
