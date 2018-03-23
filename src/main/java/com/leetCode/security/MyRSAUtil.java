package com.leetCode.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hachel on 2018/3/8
 * RSA加解密
 */
public class MyRSAUtil {

    private final static Logger logger = LoggerFactory.getLogger(MyRSAUtil.class);

    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    /**
     * String to hold name of the encryption padding.
     */
    public static final String PADDING = "RSA/None/NoPadding";

    /**
     * String to hold name of the security provider.
     */
    public static final String PROVIDER = "BC";

    /**
     * key length
     */
    public static final int KEY_LENGTH = 1024;

    /**
     * (key length)/8 = BLOCK_SIZE
     * BouncyCastleProvider可以让每个分段加密的明文和密钥的密文长度一致，使用java默认的ProviderBLOCK_SIZE-11
     */
    public static final int BLOCK_SIZE = 128;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * 私钥解密过程
     *
     * @param privateKeyStr 私钥
     * @param cipherData    密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(String privateKeyStr, byte[] cipherData)
            throws Exception {
        if (privateKeyStr == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        try {
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, PROVIDER);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            int inputLen = cipherData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            for (int i = 0; inputLen - offSet > 0; offSet = i * BLOCK_SIZE) {
                byte[] cache;
                if (inputLen - offSet > BLOCK_SIZE) {
                    cache = cipher.doFinal(cipherData, offSet, BLOCK_SIZE);
                } else {
                    cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥加密过程
     *
     * @param publicKeyStr  公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(String publicKeyStr, byte[] plainTextData)
            throws Exception {
        if (publicKeyStr == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        try {
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, PROVIDER);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            int inputLen = plainTextData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            for (int i = 0; inputLen - offSet > 0; offSet = i * BLOCK_SIZE) {
                byte[] cache;
                if (inputLen - offSet > BLOCK_SIZE) {
                    cache = cipher.doFinal(plainTextData, offSet, BLOCK_SIZE);
                } else {
                    cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                ++i;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * @return 返回map集合
     */
    public static Map<String, String> genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        } catch (NoSuchProviderException e) {
            logger.error("NoSuchProviderException", e);
        }
        // 初始化密钥对生成器，密钥大小为1024位
        keyPairGen.initialize(KEY_LENGTH, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 得到公钥
        PublicKey publicKey = keyPair.getPublic();
        Map<String, String> keyMap = new HashMap<>();
        // 得到公钥字符串
        keyMap.put("pubilcKey", Base64.encodeBase64String(publicKey.getEncoded()));
        // 得到私钥字符串
        keyMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
        return keyMap;
    }

}
