package top.easyboot.titan.util;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import top.easyboot.titan.constant.Constants;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2021-11-07 17:48
 */
public class SignUtils {

    private static final String DEFAULT_SECRET = "1qaz@WSX#$%&";

    public static String sign(String body, Map<String, String[]> params, String[] paths) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(body)) {
            sb.append(body).append(Constants.SHARP);
        }

        if (!(null == params || params.size() == 0)) {
            params.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(paramEntry -> {
                String paramValue = String.join(Constants.COMMA, Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                sb.append(paramEntry.getKey()).append(Constants.EQUAL_SIGN).append(paramValue).append(Constants.SHARP);
            });
        }

        if (ArrayUtils.isNotEmpty(paths)) {
            String pathValues = String.join(Constants.COMMA, Arrays.stream(paths).sorted().toArray(String[]::new));
            sb.append(pathValues);
        }

        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, DEFAULT_SECRET).hmacHex(sb.toString());
    }
}
