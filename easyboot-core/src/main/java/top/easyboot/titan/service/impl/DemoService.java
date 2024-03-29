package top.easyboot.titan.service.impl;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyboot.titan.bean.UserDetailsBean;
import top.easyboot.titan.constant.Constants;
import top.easyboot.titan.feign.client.DemoClient;
import top.easyboot.titan.request.QueryUserRequest;
import top.easyboot.titan.service.IDemoService;
import top.easyboot.titan.util.JsonUtils;
import top.easyboot.titan.util.RedisUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Example service
 *
 * @author: frank.huang
 * @date: 2021-11-01 21:21
 */
@Slf4j
@Service
public class DemoService implements IDemoService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DemoClient demoClient;

    @Override
    public Integer demo1() {
        return new Random().nextInt();
    }

    @Override
    public UserDetailsBean demo2(QueryUserRequest request) {
        if (StringUtils.isAllBlank(request.getName(), request.getAddress()) && Objects.isNull(request.getAge())) {
            return null;
        }
        UserDetailsBean userDetailsBean = UserDetailsBean.builder().build();
        if (StringUtils.isNotEmpty(request.getName())) {
            String jsonStr = redisUtils.get(String.format(Constants.REDIS_USER_KEY, request.getName()));
            if (StringUtils.isNotEmpty(jsonStr)) {
                return JsonUtils.parseObject(jsonStr, UserDetailsBean.class);
            }
            userDetailsBean.setName(request.getName());
        }
        if (StringUtils.isNotEmpty(request.getAddress())) {
            userDetailsBean.setAddress(request.getAddress());
        }
        if (Objects.nonNull(request.getAge())) {
            userDetailsBean.setAge(request.getAge());
        }
        redisUtils.set(String.format(Constants.REDIS_USER_KEY, request.getName()), JsonUtils.toJSONString(request), 5L, TimeUnit.MINUTES);
        return userDetailsBean;
    }

    @Override
    public List<UserDetailsBean> demo3() {
        return Lists.newArrayList(UserDetailsBean.builder()
                        .name("法外狂徒1")
                        .age(100)
                        .address("本宇宙-拉尼亚凯亚超星系团-室女座星系团-本星系群-银河系-猎户座旋臂-太阳系-地球")
                        .build(),
                UserDetailsBean.builder()
                        .name("法外狂徒2")
                        .age(200)
                        .address("本宇宙-拉尼亚凯亚超星系团-室女座星系团-本星系群-银河系-猎户座旋臂-太阳系-地球")
                        .build(),
                UserDetailsBean.builder()
                        .name("法外狂徒3")
                        .age(300)
                        .address("本宇宙-拉尼亚凯亚超星系团-室女座星系团-本星系群-银河系-猎户座旋臂-太阳系-地球")
                        .build());
    }

    @Override
    public Object demo4(QueryUserRequest request){
        return demoClient.request(() -> demoClient.getUserDetailBean(request));
    }

}
