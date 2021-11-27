package top.easyboot.titan.util;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import top.easyboot.titan.constant.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;

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
