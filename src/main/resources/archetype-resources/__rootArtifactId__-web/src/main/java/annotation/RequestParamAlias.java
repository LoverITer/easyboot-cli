#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.annotation;

import java.lang.annotation.*;

/**
 * 驼峰转下划线
 *
 * @author: frank.huang
 * @date: 2022-02-12 16:57
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamAlias {
}
