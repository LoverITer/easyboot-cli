package top.easyboot.titan.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * System unified response code
 *
 * @author frank.huang
 * @since 2021/8/21 22:13
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS,
    INVALID_PARAMS,
    NOT_FOUND,
    INTERNAL_ERROR,

    SIGN_FAIL,
    SIGN_ERROR,
    SIGN_NOT_FOUND;

    public String getCode() {
        return this.name().toLowerCase();
    }
}
