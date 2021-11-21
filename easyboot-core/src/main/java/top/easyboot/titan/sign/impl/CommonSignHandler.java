package top.easyboot.titan.sign.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.easyboot.titan.sign.SignHandler;

/**
 * @author: frank.huang
 * @date: 2021-11-13 15:34
 */
@Component
public class CommonSignHandler implements SignHandler {


    @Value("${application.easyblig-cli.app_id:app_id}")
    private String appId;
    @Value("${application.easyblig-cli.secret:secret}")
    private String secret;


    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }
}
