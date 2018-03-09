package com.leetCode.security;

/**
 * Created by Hachel on 2018/3/2
 */
public class RSAMain {

    public static void main(String[] args) throws Exception {
        String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANAwEfsAcjIlbKA6wdsFDkmoX+e/qJbYLXUsyl1aa7ZVLAbQWUPa2PVzEGjVsv3Han9Tn5oeZTzaX/bcGn2K2S9aboNrXZMqsg9G31WHw32gEMGPbj/6i+oNR8VxlTxgHb/KvHn5yeYL0N6R8uKD9DhlT7yrMJnz77YwbxabLt/9AgMBAAECgYAJon/ihuXCDltVmwKHuVcs737owcTynjSUXKtM4Ldla9Gqyw+mhUVPLrEOuP0co5L5K4ySXF/BDjYsvYj4UGm2outmmKS2SRdl9IhpoizL9q56v/8Syl0v9nWi8g36LgjYh75AFrCZRXRDBJxilnj2yaQ6+3To+HBm1SzFo8sMwQJBAPIBAqgPdhe7hTDLG/4N39S2hcaA0ShRNZqwAZV+lODEqeDXFCFkwt+TBSfRdGp584bm0JlUE2vj0LO9dCNk7y0CQQDcOmT9bx8zZrWlHT32N8HLe9/phc07vL7GWzZsV8uJjw6a6WEKDTDXidu0Sdfe8D69kygGDcgrQQxu+CDb1bYRAkBiEaK/9DQI/gbFdSmM1JaGHskrXfxkeDRe0UBzEm7GfyvQX5hax/hona2TK0Af/feADX0vBXExYYqrq/SqezapAkAbTmpDOkoGF5AiPvP12Yf/8QYccGi4JXkKIzkmSeSE0+Ydsx5DHsuVjhRd0GIh1GwTGXH2/jJ5moEuKTn5yA/hAkAS/3amUCOE/sIu5CibLMalZTAbAGXMqo1f3SByhpXBAuEpKYdfV2zkEarjVKKaeabd1DMYyWrejBkL2urUrgw+";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQMBH7AHIyJWygOsHbBQ5JqF/nv6iW2C11LMpdWmu2VSwG0FlD2tj1cxBo1bL9x2p/U5+aHmU82l/23Bp9itkvWm6Da12TKrIPRt9Vh8N9oBDBj24/+ovqDUfFcZU8YB2/yrx5+cnmC9DekfLig/Q4ZU+8qzCZ8++2MG8Wmy7f/QIDAQAB";

//        String filepath = "D:/temp file/";
//
//        RSAEncrypt.genKeyPair(filepath);

        //String plainText = Base64.encodeBase64String(AESUtil.getKey("passwordssssssss").getEncoded());
//        String plainText = "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
//        // RSAUtil.genKeyPair();
//        System.out.println("--------------公钥加密私钥解密过程-------------------");
//        //公钥加密过程
//        byte[] cipherData = RSAUtil.encrypt(publicKeyStr, plainText.getBytes());
//        String cipher = Base64.encodeBase64String(cipherData);
//        //私钥解密过程
//        byte[] res = RSAUtil.decrypt(privateKeyStr, Base64.decodeBase64(cipher));
//        String restr = new String(res);
//        Map<String, Object> keyPair = RSAUtils.genKeyPair();
//        String pub = Base64.encodeBase64String(((RSAPublicKey)keyPair.get("RSAPublicKey")).getEncoded());
//        System.out.println(pub);
//        String cipher = RSAUtils.encryptByPublicKey(plainText, pub);
//        String pri = Base64.encodeBase64String(((RSAPrivateKey)keyPair.get("RSAPrivateKey")).getEncoded());
//        System.out.println(pri);
//        String restr = RSAUtils.decryptByPrivateKey(cipher, pri);

//        System.out.println("原文：" + plainText);
//        System.out.println("密文：" + cipher);
//        System.out.println("解密：" + restr);

//        System.out.println("原文长度: " + plainText.getBytes().length);
//        System.out.println("密文长度：" + Base64.decodeBase64(cipher).length);
//        System.out.println("秘钥长度：" + Base64.decodeBase64(pri).length);
//        System.out.println("公钥长度：" + Base64.decodeBase64(pub).length);

        try {
            String pKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQMBH7";
//            AESUtil.encrypt("D:\\temp file\\aa.txt", "D:\\temp file\\bb.txt", pKeyStr);
//            AESUtil.decrypt("D:\\temp file\\bb.txt", "D:\\temp file\\cc.txt", pKeyStr);
            String plainText = "hello worldhello worldhello worldhello worldhello world";
            byte[] cipher =  AESUtil.encrypt(plainText.getBytes(), AESUtil.DEFAULT_KEY);
            byte[] restr = AESUtil.decrypt(cipher, AESUtil.DEFAULT_KEY);
            System.out.println("原文：" + plainText);
            //System.out.println("密文：" + cipher);
            System.out.println("解密：" + new String(restr));
        } catch (Exception e) {

        }

    }

}
