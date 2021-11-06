package top.easyboot.titan.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: frank.huang
 * @date: 2021-10-31 22:54
 */
public final class Constants {

    //通用
    public static final String UNDERSCORE = "_";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    //请求类
    public static final String REQUEST_ID = "RequestId";
    public static final String TX_ID = "TxId";
    public static final String TRACE_ID = "TraceId";
    public static final String SPAN_ID = "SpanId";
    public static final String SPAN_EXPORT = "SpanExport";
    public static final String REQUEST_ID_HEADER = "RequestId";
    public static final String IP = "IP";
    public static final String UNKNOWN_IP = "unknown";
    public static final String REQUEST_URL = "url";

    //缓存类
    public static final String REDIS_USER_KEY = "easyblog-cli:user:name:%s";
}
