package top.easyboot.titan.sign.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.easyboot.titan.sign.SignEntity;
import top.easyboot.titan.sign.SignHandler;

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
    public String generateSign(SignEntity signEntity) {
        return SignHandler.super.generateSign(signEntity);
    }
}
