package top.easyboot.titan.sign.impl;

import com.google.common.collect.Maps;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.easyboot.titan.constant.Constants;
import top.easyboot.titan.sign.SignEntity;
import top.easyboot.titan.sign.SignHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2021-11-21 13:40
 */
@Component
public class FeignSignHandler implements SignHandler {

    @Value("${application.easyblig-cli.app_id:app_id}")
    private String appId;
    @Value("${application.easyblig-cli.secret:secret}")
    private String secret;

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public void postSign(RequestTemplate template) {
        SignEntity signEntity = new SignEntity();
        signEntity.setMethod(template.method());
        signEntity.setPath(template.path());
        Map<String, Collection<String>> pathParams = Maps.newHashMap();
        String timestamp = String.valueOf(System.currentTimeMillis());
        pathParams.put(Constants.TIMESTAMP, Collections.singletonList(timestamp));
        pathParams.put(Constants.APP_ID,Collections.singletonList(this.getAppId()));
        pathParams.put(Constants.SECRET,Collections.singletonList(this.getSecret()));
        pathParams.putAll(getParam(template));
        signEntity.setPathParams(pathParams);
        template.query(Constants.TIMESTAMP, timestamp)
                .query(Constants.APP_ID, this.getAppId())
                .query(Constants.SECRET, this.getSecret())
                .query(Constants.SIGN, generateSign(signEntity));
    }

    private Map<String, Collection<String>> getParam(RequestTemplate template){
        return template.queries().entrySet().stream()
                .filter(entry-> CollectionUtils.isEmpty(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(o1, o2)->o1));
    }

}
