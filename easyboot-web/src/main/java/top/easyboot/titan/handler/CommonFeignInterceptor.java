package top.easyboot.titan.handler;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.easyboot.titan.sign.SignEntity;
import top.easyboot.titan.sign.SignHandler;

import java.util.Collection;

/**
 * feign 远程调用
 * @author: frank.huang
 * @date: 2021-11-21 13:42
 */
@Component
public class CommonFeignInterceptor extends SignInterceptor implements RequestInterceptor{

    @Autowired
    @Qualifier(value = "feignSignHandler")
    private SignHandler signHandler;

    @Override
    public SignHandler handler() {
        return signHandler;
    }


    @Override
    public void apply(RequestTemplate requestTemplate) {
        Collection<String> params = requestTemplate.getRequestVariables();
        Request request = requestTemplate.request();
        byte[] body = requestTemplate.body();
        String bodyTemplate = requestTemplate.bodyTemplate();
        String path = requestTemplate.path();
        String url = requestTemplate.url();
        SignEntity signEntity = new SignEntity();
        signEntity.setMethod(requestTemplate.method());
        String sign = handler().generateSign(signEntity);
    }

}
