package top.easyboot.titan.service;

import top.easyboot.titan.bean.UserDetailsBean;
import top.easyboot.titan.request.QueryUserRequest;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2021-11-20 10:09
 */
public interface IDemoService {

    Integer demo1();

    UserDetailsBean demo2(QueryUserRequest request);

    List<UserDetailsBean> demo3();

    Object demo4(QueryUserRequest request);

}
