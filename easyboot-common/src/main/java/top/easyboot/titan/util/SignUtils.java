package top.easyboot.titan.util;

/**
 * @author: frank.huang
 * @date: 2021-11-07 17:48
 */
public class SignUtils {

    public static String sign(String url) {
       return EncryptUtils.SHA256(url);
    }

    public static String md5(String url) {
        return EncryptUtils.MD5(url);
    }
}
