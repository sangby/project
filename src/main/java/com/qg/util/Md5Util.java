package com.qg.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

public class Md5Util {
    static MessageDigest messageDigest = null;
    /**
     * 盐位数
     */
    static final int SALT_LEN = 12;
    static{
        try {
            //获取信息摘要加密对象
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Md5Util() throws AssertionError {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    public static String encrypt(String message){
        Objects.requireNonNull(message);
//获取两个字节数组,分别放入加密
        //魔改一下,感觉随机盐还要写个验证函数太麻烦了

        String sa = "QGQGQG!!!";
        byte[] salt = sa.getBytes();

        byte[] mess = message.getBytes();

        messageDigest.update(salt);
        messageDigest.update(mess);

        //取出
        byte[] digest = messageDigest.digest();
        //新建数组
        byte[] encryptBytes = new byte[salt.length + digest.length];


        System.arraycopy(salt, 0, encryptBytes, 0, salt.length);

        System.arraycopy(digest, 0, encryptBytes, salt.length, digest.length);

        //易于阅读
        return Base64.getEncoder().encodeToString(encryptBytes);
    }

}
