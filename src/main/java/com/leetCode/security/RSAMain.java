package com.leetCode.security;


import org.apache.commons.codec.binary.Base64;

import java.io.File;

/**
 * Created by Hachel on 2018/3/2
 */
public class RSAMain {

    public static void main(String[] args) throws Exception {
        String aesKey = "9w0BAQddf";
        String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMi4XhkqhC/U71+NdIAA63fFlV636jMCzB2HrES1wLZn05F+qWPq3snHb+2fPEoWQnG+tIhJaHP8wDV/f5el/kdJJaNtT9SciUF/sETu7eo/ertFpIDORHTqyBzppp5bYhB4N+xDEGNMDh0QXCrAlgTCDYRkmcsKpIlzjAf4EULlAgMBAAECgYACIOpmi+vg8foj4kUlAl185KyD16F1LwdpHMI37FGB3bIBrf0v4RLBwwqt0215P6kuuZ4Cf/zKlhiXuKVqv0jmBdGc+M+fUvIWL7eXMfLKTkHFU4NGrVcTCowJTTtHzInF8ol0qy8T/dKMAsJW5nbrdzRKH6Ht5D1MlOnHAb5b4QJBAOyk6fWEB2B7fHz9zYRLBIZ8KghSTW6i7HHmwLmb3ef+cBbR0yJr3ufCa2g6CsRGh8jewDiLjx81UChRi1TId+0CQQDZIz3qgD29MRUDhBZX2ykcya3dsqMfoWSDiKUFd8VOWAdnJNNGTqDKf1LhGQqxmkm6SSqZzZSg9V4M2IlWiKfZAkEAnxVKNoN4VhMNoBUayNmJhEMKGqgBZ+Pc4cFHRD+VEPWKMr0Bm706j0Cy5dFHFaV4eL/OJvUi1M8JQOF4shlGDQJBAIryKz+w2lEDZHAAiQ+lNXSvdaulEQ8/hoNI4FhUYRSV7f7JqMyXqfqKaS2Tia1GjqE/Cpyq2c351TohIhkWj1kCQFNq6dTqOg+oN+P6FOXs2S8Vdi6fMxqJipby7wb8so5XxgugtO1oNRi9fTqzA7+NtAai0ms9P9PhcGShpaosmZE=";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIuF4ZKoQv1O9fjXSAAOt3xZVet+ozAswdh6xEtcC2Z9ORfqlj6t7Jx2/tnzxKFkJxvrSISWhz/MA1f3+Xpf5HSSWjbU/UnIlBf7BE7u3qP3q7RaSAzkR06sgc6aaeW2IQeDfsQxBjTA4dEFwqwJYEwg2EZJnLCqSJc4wH+BFC5QIDAQAB";
        String plainText = "C3T5QyCxzIJygLWWTons6MbQoqCkPUKgeJXeJx939fkWk2sqSLkB+27URSAIE7dmjl3owgPN5ItnsMX54iKLdUp89uTs0m9J1+F+BAjqrAYvX9kpXlxqTnjW6Nj6vSoB8DeEUxkrGrEJn6h7Iy7pDJqJ+ARBDaMxCFJU/3D2jz8=";
        System.out.println("--------------公钥加密私钥解密过程-------------------");
        //公钥加密过程
        // byte[] cipherData = RSAUtil.encrypt(publicKeyStr, plainText.getBytes());
        //String cipher = Base64.encodeBase64String(cipherData);
        //私钥解密过程
//        byte[] res = RSAUtil.decrypt(privateKeyStr, Base64.decodeBase64(plainText));
//        String restr = new String(res);
//
//        Map<String, String> keyPair = RSAUtil.genKeyPair();
//        //System.out.println(keyPair.get("pubilcKey"));
//        //System.out.println(keyPair.get("privateKey"));
//
//
//        System.out.println("原文：" + plainText);
//        //System.out.println("密文：" + cipher);
//        System.out.println("解密：" + restr);

//        System.out.println("原文长度: " + plainText.getBytes().length);
//        System.out.println("密文长度：" + Base64.decodeBase64(cipher).length);
//        System.out.println("秘钥长度：" + Base64.decodeBase64(pri).length);
//        System.out.println("公钥长度：" + Base64.decodeBase64(pub).length);

        String a ="GsFv/Q9xVmXJdRWo0NvCGVuOHsjjG1QFLii98J8+/z8ySAcuE9MSNtoJQ9QbJKtn5qYUTtqQyYPEs0ub8fX6xcgHP6KTQCkIxGAEUqo4bMBf0cAF7Ee3ZB0vKvxxadWKZHbK1GqDuVrsJxD6tIA+TkH5LwzSs7jvOEGtnHI7Tbo=";
        try {
            //AESUtil.encrypt("D:\\temp file\\dna.val", "D:\\temp file\\COL_90001_20180315.AVL", aesKey);
            //AESUtil.encrypt("D:\\temp file\\code.val", "D:\\temp file\\20180315_00001.dat", aesKey);
            //AESUtil.encrypt("D:\\temp file\\dna.chk", "D:\\temp file\\COL_90001_20180315.CHK", aesKey);
            // System.out.println(Base64.encodeBase64String(RSAUtil.encrypt(publicKeyStr, aesKey.getBytes())));
            decryptDnaFile(new File("D:/temp file/20180315"), aesKey);
            System.out.println(new String(RSAUtil.decrypt(privateKeyStr, Base64.decodeBase64(a))) );
        } catch (Exception e) {

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
        try {
            long start = System.currentTimeMillis();
            if (srcFile.contains(File.separator + "dna" + File.separator)) {
                String destFile = srcFile.replace("dna", "temp" + File.separator + "dna");
                AESUtil.decrypt(srcFile, destFile, key);
            } else if (srcFile.contains(File.separator + "code" + File.separator)) {
                String destFile = srcFile.replace("code", "temp" + File.separator + "code");
                AESUtil.decrypt(srcFile, destFile, key);
            } else {
            }
        } catch (Exception e) {
        }
    }

}
