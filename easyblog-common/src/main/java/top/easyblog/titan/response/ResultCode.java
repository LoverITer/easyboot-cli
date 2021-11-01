package top.easyblog.titan.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码
 *
 * @author frank.huang
 * @since 2021/8/21 22:13
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("success"),
    INVALID_PARAMS("invalid params"),
    NOT_FOUND("resource not found"),
    INTERNAL_ERROR("internal error")
    ;

    private final String message;


    public String getCode(){
        return this.name().toLowerCase();
    }
}
