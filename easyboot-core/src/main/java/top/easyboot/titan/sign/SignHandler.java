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
     *
     * @param signEntity
     * @return
     */
    default String generateSign(SignEntity signEntity) {
        StringBuilder appender = new StringBuilder(signEntity.getMethod() + " " + signEntity.getPath() + "?");
        Map<String, String[]> pathParams = signEntity.getPathParams();
        if (!CollectionUtils.isEmpty(pathParams)) {
            pathParams.entrySet().stream().filter(entry -> !Constants.SIGN.equalsIgnoreCase(entry.getKey()))
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                String paramValue = String.join(Constants.COMMA, Arrays.stream(entry.getValue()).sorted().toArray(String[]::new));
                appender.append(entry.getKey()).append(Constants.EQUAL_SIGN).append(paramValue).append(Constants.DELIMETER);
            });
        }
        return SignUtils.sign(appender.substring(0, appender.length() - 1));
    }

    default boolean checkSign(SignEntity signEntity) throws Exception {
        if (Objects.isNull(signEntity)) {
            throw new BusinessException(ResultCode.SIGN_FAIL);
        }
        Map<String, String[]> params = signEntity.getPathParams();
        String[] requestTimestamp = params.get(Constants.TIMESTAMP);
        if (null==requestTimestamp||requestTimestamp.length==0||StringUtils.isBlank(requestTimestamp[0])) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "invalid request");
        }
        long timestamp = Long.parseLong(requestTimestamp[0]);
        long now = new Date().getTime();
        if (now - timestamp > Constants.TEN_MINUS) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign expired");
        }
        String[] appId = params.get(Constants.APP_ID);
        if (null==appId||appId.length==0||StringUtils.isBlank(appId[0])) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "invalid request");
        }
        String[] secret = params.get(Constants.SECRET);
        if (null==secret||secret.length==0||StringUtils.isBlank(secret[0])) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "invalid request");
        }
        String[] oldSign = params.get(Constants.SIGN);
        if (null==oldSign||oldSign.length==0||StringUtils.isBlank(oldSign[0])) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "invalid request");
        }
        String newSign = generateSign(signEntity);
        if (!oldSign[0].equals(newSign)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign invalid");
        }
        return true;
    }


}
