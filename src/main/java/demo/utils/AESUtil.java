package demo.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * AES加、解密方式
 * @author admin
 * 2022/8/17 20:23
 **/
public class AESUtil {

    // 指定AES算法 key
    private static final String KEY_AES = "AES";
    // 加密秘钥
    private static final String KEY = "9SRoTbF9tdw4ymbY";

    /**
     *  加密
     * @param data 待加密内容
     */
    public static String encrypt(String data) {
        return doAES(data, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * @param data 带解密内容
     */
    public static String decrypt(String data) {
        return doAES(data, Cipher.DECRYPT_MODE);
    }

    /**
     * 加、解密
     * @param data 待处理数据
     * @param mode 加解密mode
     *
     */
    private static String doAES(String data, int mode) {
        try {
            // 判断是否是加密还是解密
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            // true 加密内容， false解密内容
            if (encrypt) {
                content = data.getBytes(StandardCharsets.UTF_8);
            } else {
                content = parseHexStr2Byte(data);
            }
            if (content == null) {
                return null;
            }
            // 1.构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            // 2.根据encodeRules规则初始化密钥生成器
            kgen.init(128, new SecureRandom(KEY.getBytes()));
            // 3.产生原始对称密钥
            SecretKey secretKey = kgen.generateKey();
            // 4. 获得原始对称密钥的字节数组
            byte[] encodeFormat = secretKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey keySpec = new SecretKeySpec(encodeFormat, KEY_AES);
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);
            // 7.初始化密码器，第一个参数为加密或者解密操作，第二个参数为使用的KEY
            cipher.init(mode, keySpec);
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                // 将二进制转换成16进制
                return parseByte2HexStr(result);
            } else {
                return new String(result, StandardCharsets.UTF_8);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder builder = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            builder.append(hex.toUpperCase());
        }
        return builder.toString();
    }

    /**
     * 将16进制转换成二进制
     *
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String data = "1000@qq.com";
        String encrypt = encrypt(data);
        System.out.println("加密后的值： " + encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println("解密后的值：" + decrypt);
    }
}
