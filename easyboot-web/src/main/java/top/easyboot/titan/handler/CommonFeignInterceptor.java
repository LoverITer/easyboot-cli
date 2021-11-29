package top.easyboot.titan.handler;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import top.easyboot.sign.SignHandler;

/**
 * feign 远程调用
 * @author: frank.huang
 * @date: 2021-11-21 13:42
 */
@Slf4j
public class CommonFeignInterceptor implements RequestInterceptor{

    //@Autowired
    //@Qualifier(value = "feignSignHandler")
    private SignHandler signHandler;

    //@Override
    public SignHandler handler() {
        return signHandler;
    }


    @Override
    public void apply(RequestTemplate requestTemplate) {
         try{
             //handler().postSign(requestTemplate);
         }catch (Exception e){
             log.error("CommonFeignInterceptor>>>>>>{}",e.getMessage());
             throw e;
         }
    }

}
