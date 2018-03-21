package com.leetCode;


import com.ai.obc.iop.cache.RedisTemplateUtil;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Comparator;

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
            RedisTemplate<String, String> stringRedisTemplate = RedisTemplateUtil.newInstance(String.class);
            ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
            //stringRedisTemplate.delete("test");
//            listOperations.leftPush("test", "1");
//            listOperations.leftPush("test", "2");
           // listOperations.leftPush("test", "3");
          //  listOperations.leftPush("test", "4");

            System.out.println(listOperations.index("test", 3));
//            File file = new File("D:/temp file/bb.txt");
//            System.out.println(file.getAbsolutePath());
//            System.out.println(file.isFile());
//            System.out.println(file.isDirectory());
//            System.out.println(file.getName().toUpperCase().contains(".TXT"));

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

//            String url = "http://127.0.0.1:8080/demo/hello?publicKey=%s";
//            url = String.format(url, URLEncoder.encode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIuF4ZKoQv1O9fjXSAAOt3xZVet+ozAswdh6xEtcC2Z9ORfqlj6t7Jx2/tnzxKFkJxvrSISWhz/MA1f3+Xpf5HSSWjbU/UnIlBf7BE7u3qP3q7RaSAzkR06sgc6aaeW2IQeDfsQxBjTA4dEFwqwJYEwg2EZJnLCqSJc4wH+BFC5QIDAQAB", "utf-8"));
//            boolean a = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIuF4ZKoQv1O9fjXSAAOt3xZVet+ozAswdh6xEtcC2Z9ORfqlj6t7Jx2/tnzxKFkJxvrSISWhz/MA1f3+Xpf5HSSWjbU/UnIlBf7BE7u3qP3q7RaSAzkR06sgc6aaeW2IQeDfsQxBjTA4dEFwqwJYEwg2EZJnLCqSJc4wH+BFC5QIDAQAB".equals("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIuF4ZKoQv1O9fjXSAAOt3xZVet+ozAswdh6xEtcC2Z9ORfqlj6t7Jx2/tnzxKFkJxvrSISWhz/MA1f3+Xpf5HSSWjbU/UnIlBf7BE7u3qP3q7RaSAzkR06sgc6aaeW2IQeDfsQxBjTA4dEFwqwJYEwg2EZJnLCqSJc4wH+BFC5QIDAQAB");
//            System.out.println(a);
//            String response = HttpUtil.doPost(url);
//            JSONObject jsonObject = JSON.parseObject(response);
//            System.out.println(jsonObject.get("1"));
        //    System.out.println(response);

//            Test001 test001 = new Test001();
//            System.out.println(test001.getEntity());
//
//            Test001 test002 = new Test001();
//            System.out.println(test002.getEntity());
//            char sp = 1;
//            List<String> bookList = new ArrayList<>();
//            bookList.add("20180315_00001" + sp + "2");
//            bookList.add("20180315_00001" + sp + "1");
//            bookList.add("20180315_00001" + sp + "3");
//            Collections.sort(bookList, new ComparatorBook());
//            System.out.println(bookList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static class ComparatorBook implements Comparator<String> {
        private char sp = 1;

        @Override
        public int compare(String o1, String o2) {
            try {
                long o1Stamp = Long.valueOf(o1.split(String.valueOf(sp))[1]);
                long o2Stamp = Long.valueOf(o2.split(String.valueOf(sp))[1]);
                if (o1Stamp > o2Stamp) {
                    return -1;
                }
            } catch (Exception e) {

            }
            return 1;
        }
    }
}
