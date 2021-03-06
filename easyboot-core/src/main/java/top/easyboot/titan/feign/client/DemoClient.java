package top.easyboot.titan.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import top.easyboot.titan.bean.UserDetailsBean;
import top.easyboot.titan.feign.config.CommonFeignConfig;
import top.easyboot.titan.feign.internal.BaseClientResponse;
import top.easyboot.titan.feign.internal.Verify;
import top.easyboot.titan.request.QueryUserRequest;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:29
 */
@FeignClient(name = "demo",url = "${urls.demo}",configuration = CommonFeignConfig.class)
public interface DemoClient extends Verify {

    @GetMapping(value = "/v1/demo/objects")
    BaseClientResponse<UserDetailsBean> getUserDetailBean(@SpringQueryMap QueryUserRequest request);


}
