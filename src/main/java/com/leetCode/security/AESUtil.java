package com.leetCode.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Created by Hachel on 2018/3/7
 * <p>
 * 使用AES对文件进行加密和解密
 */
public class AESUtil {

    private final static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 密钥长度
     */
    public static final int KEY_LENGTH = 128;

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


    /**
     * 把文件srcFile加密后存储为destFile
     *
     * @param srcFile    加密前的文件
     * @param destFile   加密后的文件
     * @param privateKey 密钥
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void encrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
        Key key = getKey(privateKey);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(destFile));

            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 把文件srcFile解密后存储为destFile
     *
     * @param srcFile    解密前的文件
     * @param destFile   解密后的文件
     * @param privateKey 密钥
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void decrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
        Key key = getKey(privateKey);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(destFile));

            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 根据filePath创建相应的目录
     *
     * @param filePath 要创建的文件路经
     * @return file        文件
     * @throws IOException
     */
    private File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }

    /**
     * 生成指定字符串的密钥
     *
     * @param secret 要生成密钥的字符串
     * @return secretKey    生成后的密钥
     * @throws GeneralSecurityException
     */
    private static Key getKey(String secret) throws GeneralSecurityException {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        kgen.init(KEY_LENGTH, new SecureRandom(secret.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        return secretKey;
    }

    /**
     * 加密解密流
     *
     * @param in     加密解密前的流
     * @param out    加密解密后的流
     * @param cipher 加密解密
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0) {
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        } else {
            outBytes = cipher.doFinal();
        }
        out.write(outBytes);
    }
}