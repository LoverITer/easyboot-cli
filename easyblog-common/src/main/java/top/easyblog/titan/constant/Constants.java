package top.easyblog.titan.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: frank.huang
 * @date: 2021-10-31 22:54
 */
public final class Constants {

    public static final String UNDERSCORE = "_";
    public static final String OK = "ok";
    public static final String SERVER_ERROR = "internal error";
    public static final String UNKNOWN_IP = "unknown";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static final String REQUEST_ID = "X-Request-ID";
}
