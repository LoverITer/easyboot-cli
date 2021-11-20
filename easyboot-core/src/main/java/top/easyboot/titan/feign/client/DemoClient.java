package top.easyboot.titan.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.easyboot.titan.feign.config.FeignConfiguration;
import top.easyboot.titan.feign.internal.BaseClientResponse;
import top.easyboot.titan.feign.internal.Verify;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:29
 */
@FeignClient(name = "demo",url = "${urls.demo}",configuration = FeignConfiguration.class)
public interface DemoClient extends Verify {

    @GetMapping(value = "/")
    BaseClientResponse<Object> fetchBaidu();


}
