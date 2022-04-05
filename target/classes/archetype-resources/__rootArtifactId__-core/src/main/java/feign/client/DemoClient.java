#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import ${package}.bean.UserDetailsBean;
import ${package}.feign.config.CommonFeignConfig;
import ${package}.feign.internal.BaseClientResponse;
import ${package}.feign.internal.Verify;
import ${package}.request.QueryUserRequest;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:29
 */
@FeignClient(name = "demo",url = "${symbol_dollar}{urls.demo}",configuration = CommonFeignConfig.class)
public interface DemoClient extends Verify {

    @GetMapping(value = "/v1/demo/objects")
    BaseClientResponse<UserDetailsBean> getUserDetailBean(@SpringQueryMap QueryUserRequest request);


}
