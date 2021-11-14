package top.easyboot.titan.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyboot.titan.sign.SignHandler;

/**
 * @author: frank.huang
 * @date: 2021-11-13 15:22
 */
@Component
public class CommonSignInterceptor extends SignInterceptor{

    @Autowired
    private SignHandler signHandler;


    @Override
    public SignHandler handler() {
        return signHandler;
    }
}
