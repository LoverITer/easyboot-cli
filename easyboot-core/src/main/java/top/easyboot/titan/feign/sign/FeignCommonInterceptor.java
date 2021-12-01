package top.easyboot.titan.feign.sign;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyboot.constant.Constants;
import top.easyboot.sign.SignEntity;
import top.easyboot.sign.SignHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2021-12-01 20:36
 */
@Component
public class FeignCommonInterceptor implements RequestInterceptor {

    @Autowired
    private SignHandler signHandler;

    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Request request = requestTemplate.request();
        String httpMethod = request.httpMethod().toString().toUpperCase();
        Map<String, Collection<String>> requestParams = new HashMap<>(requestTemplate.queries());
        SignEntity signEntity = new SignEntity();
        signEntity.setMethod(httpMethod);
        String url = request.url();
        int index = url.indexOf("?");
        if (index != -1) {
            signEntity.setPath(url.substring(0, index));
        } else {
            signEntity.setPath(url);
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestParams.put(Constants.TIMESTAMP, Lists.newArrayList(timestamp));
        signEntity.setPathParams(requestParams);
        HashMap<String, Collection<String>> signQueriesParams = Maps.newHashMap();
        signQueriesParams.put(Constants.APP_ID, Collections.singleton(signHandler.getAppId()));
        signQueriesParams.put(Constants.TIMESTAMP, Collections.singleton(timestamp));
        signQueriesParams.put(Constants.SIGN, Collections.singleton(signHandler.generateRequestSign(signEntity)));

        requestTemplate.queries(signQueriesParams);
        String url1 = requestTemplate.request().url();
    }
}
