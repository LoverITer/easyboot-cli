package top.easyboot.titan.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import top.easyboot.titan.exception.BusinessException;
import top.easyboot.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
public class CommonFeignConfig extends FeignConfig {

    @Bean
    public ErrorDecoder error() {
        return (s, response) -> {
            throw new BusinessException(ResultCode.REMOTE_INVOKE_FAIL, "远程调用失败");
        };
    }

}
