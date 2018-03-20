package com.leetCode;


import com.leetCode.model.Test001;

import java.io.File;

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
            File file = new File("D:/temp file/bb.txt");
            System.out.println(file.getAbsolutePath());
            System.out.println(file.isFile());
            System.out.println(file.isDirectory());
            System.out.println(file.getName().toUpperCase().contains(".TXT"));

//            String a = "D:/temp file/20180313/dna/bb.txt";
//            System.out.println( a.replace("dna", "temp/dna"));
//            System.out.println(a.contains("/dna/"));
//
//            Map<String, String> h = new HashMap<>();
//            System.out.println(h.get("d"));
//
//            String fmt = "a%sms";
//            System.out.println(String.format(fmt, 22));

            //System.out.println(MessageFormat.format("a{0}", 123));

//            String url = "http://127.0.0.1:8080/demo/hello?publicKey=ssss";
//            String response = HttpUtil.doPost(url);
//            JSONObject jsonObject = JSON.parseObject(response);
//            System.out.println(jsonObject.get("1"));
//            System.out.println(response);

            Test001 test001 = new Test001();
            System.out.println(test001.getEntity());

            Test001 test002 = new Test001();
            System.out.println(test002.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
