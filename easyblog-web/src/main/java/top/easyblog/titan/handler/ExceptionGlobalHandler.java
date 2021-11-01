package top.easyblog.titan.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.BaseResponse;
import top.easyblog.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2021-11-01 17:40
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionGlobalHandler {

    /**
     * 处理未捕获的Exception
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception e){
        log.error(e.getMessage(),e);
        return new BaseResponse(null, ResultCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理未捕获的RuntimeException
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleRuntimeException(RuntimeException e){
        log.error(e.getMessage(),e);
        return new BaseResponse(null, ResultCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理未捕获的自定义业务异常BusinessException
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse handleBusinessException(BusinessException e){
        log.error(e.getMessage(),e);
        return new BaseResponse(null, ResultCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }
}
