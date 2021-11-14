package top.easyboot.titan.sign;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import top.easyboot.titan.constant.Constants;
import top.easyboot.titan.exception.BusinessException;
import top.easyboot.titan.response.ResultCode;
import top.easyboot.titan.util.SignUtils;

import java.util.*;

/**
 * @author: frank.huang
 * @date: 2021-11-13 14:27
 */
public interface SignHandler {

    default String getSecret() {
        throw new IllegalArgumentException("secret can not be empty");
    }

    default String getAppId() {
        throw new IllegalArgumentException("appId can not be empty");
    }

    /**
     * 根据请求参数重新计算验签值
     * 验签字符串拼接规则：${请求方法} ${请求路径}?appId=${appId}&secret=${secret}&timestamp=${timestamp}&param1=${value1}....
     * @param signEntity
     * @return
     */
    default String generateSign(SignEntity signEntity) {
        StringBuilder appender = new StringBuilder(signEntity.getMethod()+" "+signEntity.getPath()+"?");
        Map<String, String> headers = signEntity.getHeaders();
        appender.append(Constants.APP_ID).append(Constants.EQUAL_SIGN).append(headers.get(Constants.APP_ID)).append(Constants.DELIMETER);
        appender.append(Constants.SECRET).append(Constants.EQUAL_SIGN).append(headers.get(Constants.SECRET)).append(Constants.DELIMETER);
        appender.append(Constants.TIMESTAMP).append(Constants.EQUAL_SIGN).append(headers.get(Constants.TIMESTAMP)).append(Constants.DELIMETER);

        Map<String, String[]> pathParams = signEntity.getPathParams();
        if(!CollectionUtils.isEmpty(pathParams)){
            pathParams.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(paramEntry -> {
                String paramValue = String.join(Constants.COMMA, Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                appender.append(paramEntry.getKey()).append(Constants.EQUAL_SIGN).append(paramValue).append(Constants.DELIMETER);
            });
        }
        return SignUtils.sign(appender.substring(0, appender.length() - 1));
    }

    default boolean checkSign(SignEntity signEntity) throws Exception {
        if (Objects.isNull(signEntity)) {
            throw new BusinessException(ResultCode.SIGN_FAIL);
        }
        String requestTimestamp = signEntity.getHeaders().get(Constants.TIMESTAMP);
        if (StringUtils.isEmpty(requestTimestamp)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "timestamp is empty");
        }
        long timestamp = Long.parseLong(requestTimestamp);
        long now = new Date().getTime();
        if (now - timestamp > Constants.TEN_MINUS) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign expired");
        }
        String appId = signEntity.getHeaders().get(Constants.APP_ID);
        if (StringUtils.isEmpty(appId)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, " appId is empty");
        }
        String secret = signEntity.getHeaders().get(Constants.SECRET);
        if (StringUtils.isEmpty(secret)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "secret is empty");
        }
        String oldSign = signEntity.getSign();
        if (StringUtils.isEmpty(oldSign)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign not found");
        }
        String newSign = generateSign(signEntity);
        if (!oldSign.equals(newSign)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign invalid");
        }
        return true;
    }


}
