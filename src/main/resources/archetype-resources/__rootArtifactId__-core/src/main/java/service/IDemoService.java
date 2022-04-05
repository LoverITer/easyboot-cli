#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import ${package}.bean.UserDetailsBean;
import ${package}.request.QueryUserRequest;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2021-11-20 10:09
 */
public interface IDemoService {

    Integer demo1();

    List<UserDetailsBean> demo3();

    Object demo4(QueryUserRequest request);

}
