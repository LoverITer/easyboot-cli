#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.request;

/**
 * @author frank.huang
 * @date 2022/01/29 14:42
 */
public interface BaseRequest {
    /**
     * 请求参校验
     */
    default boolean validate() {
        return true;
    }

}
