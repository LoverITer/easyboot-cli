#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import feign.RequestTemplate;

/**
 * @author: frank.huang
 * @date: 2022-03-01 20:13
 */
public class InterceptorUtils {

    public static void query(RequestTemplate template, String key, String value) {
        template.query(key, value);
    }

    public static String now() {
        return String.valueOf(System.currentTimeMillis());
    }
    
}
