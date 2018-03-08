package com.leetCode.rsa;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Hachel on 2018/3/2
 */
public class RSAMain {

    public static void main(String[] args) throws Exception {
        String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIM4Mzqkd+n0s9oLj/hSdwVn8IdeZnVPBwpXiLf3agLwY+HlxgIPz5rCAOyC4UvyK3Pba7zQTmcQsi7AWG9a+R8SgeEYybTkE2att5r9Z5LoKlLzm4HutXuUPHV77dYjMzUDQgB5V6n5egtylPDnLcpKW0p+qLB8x2Tca4T2lFapAgMBAAECgYBTtXZ44pLc3Q4e+2FMuM7U1AnNUZtrGo8TOeQt99fZKCGP6lT2279Eqp6MQrIXENZHgqKoQPrDJROGX44K+64QB+4XiG3Yx9VVhbQHktg6zv4ivC3tn6k5zJrPtut9FOqex1vLR5QFkak3rnkixxj2wRUeroRZeSb4rJF9TozNQQJBANUQ6dXl+JwIuXbKLOVHaocnihvNT4QZdVPvoBEesVn1gZ4MaGoph+TJPnAa15R2tVfbiIaEsr0xBozBy6UZLrsCQQCdqTR0Iut4DzMTAucME4ikERPskka2GovAORDgHFIk/kpbixtrLg140zrJ8TmN6oe1Y9ZTPFZUgGlcPTVyusPrAkBrNFJjtI2NfPpMYdeLGY7pKCTebkRXFDQrooMoeggEWnn4WwfierkMjZFj+K2F3uDJrwUXogcJGHTSN+ROUshnAkEAhFxFeSrH3KeGDXPmXTEZCgR8YaP8UW2/3cUK8dtU61HBQ1Fp13V2QfWz6U3nL8mx3QrQKFkDcTXNPm70rDk3uQJACP9wRwZiVaVJlEJqZJSmHkNNDAQV3DtVNZo5MrSfa5BRWMWYEJyPIXdKJrm5FPNR06ObfUtLEcQHoXGkFmA+wg==";

        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDODM6pHfp9LPaC4/4UncFZ/CHXmZ1TwcKV4i392oC8GPh5cYCD8+awgDsguFL8itz22u80E5nELIuwFhvWvkfEoHhGMm05BNmrbea/WeS6CpS85uB7rV7lDx1e+3WIzM1A0IAeVep+XoLcpTw5y3KSltKfqiwfMdk3GuE9pRWqQIDAQAB";

        String filepath = "D:/temp file/";

        RSAEncrypt.genKeyPair(filepath);

        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "公钥加密私钥解密0000001";
        //公钥加密过程
        byte[] cipherData = RSAEncrypt.encrypt(publicKeyStr, plainText.getBytes());
        String cipher = Base64.encodeBase64String(cipherData);
        //私钥解密过程
        byte[] res = RSAEncrypt.decrypt(privateKeyStr, Base64.decodeBase64(cipher));
        String restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);

    }

}
