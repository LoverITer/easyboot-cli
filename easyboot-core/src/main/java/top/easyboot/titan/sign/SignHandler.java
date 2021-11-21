package top.easyboot.titan.sign;

import feign.RequestTemplate;
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

    /**
     * 获取系统secret
     *
     * @return
     */
    default String getSecret() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取系统app_id
     *
     * @return
     */
    default String getAppId() {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据请求参数计算验签值
     * 验签字符串拼接规则：${请求方法} ${请求路径}?appId=${appId}&secret=${secret}&timestamp=${timestamp}&param1=${value1}....
     *
     * @param signEntity
     * @return
     */
    default String generateSign(SignEntity signEntity) {
        StringBuilder appender = new StringBuilder(signEntity.getMethod() + " " + signEntity.getPath() + "?");
        Map<String, Collection<String>> pathParams = signEntity.getPathParams();
        if (!CollectionUtils.isEmpty(pathParams)) {
            pathParams.entrySet().stream().filter(entry -> !Constants.SIGN.equalsIgnoreCase(entry.getKey()))
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        String paramValue = String.join(Constants.COMMA, entry.getValue());
                        appender.append(entry.getKey()).append(Constants.EQUAL_SIGN).append(paramValue).append(Constants.DELIMETER);
                    });
        }
        return SignUtils.sign(appender.substring(0, appender.length() - 1));
    }

    /**
     * 外部调用本系统时，对传入的验签值进行校验
     *
     * @param signEntity
     * @return
     * @throws Exception
     */
    default boolean checkSign(SignEntity signEntity) throws Exception {
        if (Objects.isNull(signEntity)) {
            throw new BusinessException(ResultCode.SIGN_FAIL);
        }
        Map<String, Collection<String>> params = signEntity.getPathParams();
        getSignParam(Constants.TIMESTAMP,params);
        getSignParam(Constants.APP_ID,params);
        getSignParam(Constants.SECRET,params);
        String oldSign = getSignParam(Constants.SIGN, params);
        String newSign = generateSign(signEntity);
        if (!oldSign.equals(newSign)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign invalid");
        }
        return true;
    }

    default String getSignParam(String name, Map<String, Collection<String>> params){
        Collection<String> paramCollection = params.get(name);
        if(CollectionUtils.isEmpty(paramCollection)){
            throw new BusinessException(ResultCode.SIGN_FAIL, "invalid request");
        }
        Object[] paramArray = paramCollection.toArray();
        String paramValue=(String)paramArray[0];
        if(StringUtils.isBlank(paramValue)){
            throw new BusinessException(ResultCode.SIGN_FAIL, "invalid request");
        }

        if(Constants.TIMESTAMP.equalsIgnoreCase(name)){
            long timestamp = Long.parseLong(paramValue);
            long now = new Date().getTime();
            if (now - timestamp > Constants.TEN_MINUS) {
                throw new BusinessException(ResultCode.SIGN_FAIL, "sign expired");
            }
        }
        return paramValue;
    }

    /**
     * 调用其他系统接口时进行验签参数自动填充
     *
     * @param template
     */
    default void postSign(RequestTemplate template) {
        throw new UnsupportedOperationException();
    }


}
