package com.leetCode.security;



import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by Hachel on 2018/3/2
 */
public class RSAMain {

    public static void main(String[] args) throws Exception {
        String aesKey = "1]#Omc70";
        String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMi4XhkqhC/U71+NdIAA63fFlV636jMCzB2HrES1wLZn05F+qWPq3snHb+2fPEoWQnG+tIhJaHP8wDV/f5el/kdJJaNtT9SciUF/sETu7eo/ertFpIDORHTqyBzppp5bYhB4N+xDEGNMDh0QXCrAlgTCDYRkmcsKpIlzjAf4EULlAgMBAAECgYACIOpmi+vg8foj4kUlAl185KyD16F1LwdpHMI37FGB3bIBrf0v4RLBwwqt0215P6kuuZ4Cf/zKlhiXuKVqv0jmBdGc+M+fUvIWL7eXMfLKTkHFU4NGrVcTCowJTTtHzInF8ol0qy8T/dKMAsJW5nbrdzRKH6Ht5D1MlOnHAb5b4QJBAOyk6fWEB2B7fHz9zYRLBIZ8KghSTW6i7HHmwLmb3ef+cBbR0yJr3ufCa2g6CsRGh8jewDiLjx81UChRi1TId+0CQQDZIz3qgD29MRUDhBZX2ykcya3dsqMfoWSDiKUFd8VOWAdnJNNGTqDKf1LhGQqxmkm6SSqZzZSg9V4M2IlWiKfZAkEAnxVKNoN4VhMNoBUayNmJhEMKGqgBZ+Pc4cFHRD+VEPWKMr0Bm706j0Cy5dFHFaV4eL/OJvUi1M8JQOF4shlGDQJBAIryKz+w2lEDZHAAiQ+lNXSvdaulEQ8/hoNI4FhUYRSV7f7JqMyXqfqKaS2Tia1GjqE/Cpyq2c351TohIhkWj1kCQFNq6dTqOg+oN+P6FOXs2S8Vdi6fMxqJipby7wb8so5XxgugtO1oNRi9fTqzA7+NtAai0ms9P9PhcGShpaosmZE=";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIuF4ZKoQv1O9fjXSAAOt3xZVet+ozAswdh6xEtcC2Z9ORfqlj6t7Jx2/tnzxKFkJxvrSISWhz/MA1f3+Xpf5HSSWjbU/UnIlBf7BE7u3qP3q7RaSAzkR06sgc6aaeW2IQeDfsQxBjTA4dEFwqwJYEwg2EZJnLCqSJc4wH+BFC5QIDAQAB";
        String plainText = "YgpfCmXBERCNoDMSzi2P9aUx9tzTgLnqKGji4+Cx4PNsUvmXb/nRq5ptHkc++v6wqVMKBGqPKOm1ywIfifshNHu68SINzw49XK23a9TxD/4KWuxypvFV6qwz+N5ljcobbJp0r1CPqIzJVz5yUZZ0rq+SI0wYedTj+M0EetnZCnc=";
        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String rsaPublicKey = "MGcwDQYJKoZIhvcNAQEBBQADVgAwUwJMAIT/IeHySAK3pjddw5WUR8tyCozjuW2Smi8JuONyFnTaeZxHQa7Ruiwz+M0IzHxQmBj0R1N5/aFs9GBaivuWWuHnZYL6QMq8iRn8cwIDAQAB";


        try {
            //MyAESUtil.encrypt("D:\\temp file\\dna.val", "D:\\temp file\\COL_90001_20180315.AVL", aesKey);
            //MyAESUtil.encrypt("D:\\temp file\\code.val", "D:\\temp file\\20180315_00001.dat", aesKey);
            //MyAESUtil.encrypt("D:\\temp file\\dna.chk", "D:\\temp file\\COL_90001_20180315.CHK", aesKey);
            // System.out.println(Base64.encodeBase64String(MyRSAUtil.encrypt(publicKeyStr, aesKey.getBytes())));
            decryptDnaFile(new File("D:\\temp file\\20180320"), aesKey);
            // System.out.println(new String(MyRSAUtil.decrypt(privateKeyStr, Base64.decodeBase64(a))) );

//            String json = "{\"PASSWD_ID\":\"39ea8ce9c7ed4268a2d28b8cc0d10a78\",\"PASSWD\":\"YgpfCmXBERCNoDMSzi2P9aUx9tzTgLnqKGji4+Cx4PNsUvmXb/nRq5ptHkc++v6wqVMKBGqPKOm1ywIfifshNHu68SINzw49XK23a9TxD/4KWuxypvFV6\n" +
//                    "qwz+N5ljcobbJp0r1CPqIzJVz5yUZZ0rq+SI0wYedTj+M0EetnZCnc=\"}";
//            JSONObject jsonObject = JSONObject.parseObject(json);
//            // System.out.println(jsonObject.get("PASSWD_ID"));
//            System.out.println(jsonObject.get("PASSWD"));
//            String pri = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMi4XhkqhC/U71+NdIAA63fFlV636jMCzB2HrES1wLZn05F+qWPq3snHb+2fPEoWQnG+tIhJaHP8wDV/f5el/kdJJaNtT9SciUF/sETu7eo/ertFpIDORHTqyBzppp5bYhB4N+xDEGNMDh0QXCrAlgTCDYRkmcsKpIlzjAf4EULlAgMBAAECgYACIOpmi+vg8foj4kUlAl185KyD16F1LwdpHMI37FGB3bIBrf0v4RLBwwqt0215P6kuuZ4Cf/zKlhiXuKVqv0jmBdGc+M+fUvIWL7eXMfLKTkHFU4NGrVcTCowJTTtHzInF8ol0qy8T/dKMAsJW5nbrdzRKH6Ht5D1MlOnHAb5b4QJBAOyk6fWEB2B7fHz9zYRLBIZ8KghSTW6i7HHmwLmb3ef+cBbR0yJr3ufCa2g6CsRGh8jewDiLjx81UChRi1TId+0CQQDZIz3qgD29MRUDhBZX2ykcya3dsqMfoWSDiKUFd8VOWAdnJNNGTqDKf1LhGQqxmkm6SSqZzZSg9V4M2IlWiKfZAkEAnxVKNoN4VhMNoBUayNmJhEMKGqgBZ+Pc4cFHRD+VEPWKMr0Bm706j0Cy5dFHFaV4eL/OJvUi1M8JQOF4shlGDQJBAIryKz+w2lEDZHAAiQ+lNXSvdaulEQ8/hoNI4FhUYRSV7f7JqMyXqfqKaS2Tia1GjqE/Cpyq2c351TohIhkWj1kCQFNq6dTqOg+oN+P6FOXs2S8Vdi6fMxqJipby7wb8so5XxgugtO1oNRi9fTqzA7+NtAai0ms9P9PhcGShpaosmZE=";
//            byte[] byteKey = RSAUtil.decrypt(pri, Base64.decodeBase64(jsonObject.getString("PASSWD")));
//            System.out.println(new String(byteKey));

            String a = "我是原文";
//            byte[] b = AESUtil.encrypt(a.getBytes(), "qwertyui");
//            AESUtil.decrypt(b, "asdfghjk");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void decryptDnaFile(File rootFile, String privateKey) {
        try {
            if (!rootFile.isDirectory()) {
                return;
            }
            // 获得路径下的所有文件
            File[] listFiles = rootFile.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    decryptDnaFile(file, privateKey);
                } else {
                    decryptFile(file, privateKey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decryptFile(File file, String key) {
        String srcFile = file.getAbsolutePath();
        String dnaTempFile = File.separator + "temp" + File.separator + "dna" + File.separator;
        String codeTempFile = File.separator + "temp" + File.separator + "code" + File.separator;
        try {
            long start = System.currentTimeMillis();

            if (file.getParentFile().getName().equals("dna")) {
                String destFile = file.getParentFile().getParentFile().getAbsolutePath() + dnaTempFile + file.getName();
                dealFile(file, srcFile, destFile, key);
            } else if (file.getParentFile().getName().equals("code")) {
                String destFile = file.getParentFile().getParentFile().getAbsolutePath() + codeTempFile + file.getName();
                dealFile(file, srcFile, destFile, key);
            } else {
            }
        } catch (Exception e) {
        }
    }

    private static void dealFile(File file, String srcFile, String destFile, String key) {
        try {
            String fileName = file.getName();
            if (fileName.toUpperCase().contains(".AVL")) {
               // AESUtil.decrypt(srcFile, destFile, key);
            } else if (fileName.toUpperCase().contains(".CHK")) {
                FileUtils.copyFile(file, new File(destFile));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
