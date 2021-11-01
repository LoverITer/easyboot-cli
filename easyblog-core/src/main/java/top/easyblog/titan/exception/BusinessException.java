package top.easyblog.titan.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import top.easyblog.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2021-11-01 17:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private ResultCode code;

    public BusinessException(ResultCode code) {
        this.code = code;
    }

    public BusinessException(Throwable cause, ResultCode code) {
        super(cause);
        this.code = code;
    }

}
