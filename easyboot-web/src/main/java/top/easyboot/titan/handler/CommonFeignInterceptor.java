package top.easyboot.titan.handler;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
         try{
             handler().postSign(requestTemplate);
         }catch (Exception e){
             log.error("CommonFeignInterceptor>>>>>>{}",e.getMessage());
             throw e;
         }
    }

}
