package top.easyblog.titan.aspect;

import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.response.BaseResponse;
import top.easyblog.titan.response.ResultCode;

/**
 * 响应增强类
 *
 * @author: frank.huang
 * @date: 2021-11-01 18:28
 */
@ControllerAdvice
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getAnnotatedElement().isAnnotationPresent(ResponseWrapper.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class selectedClassType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
            // 判断响应的Content-Type为JSON格式的body
            if (body instanceof BaseResponse) {
                // 如果响应返回的对象为统一响应体，则直接返回body
                return body;
            }
        }
        // 非JSON格式body直接返回即可
        return new BaseResponse(body, MDC.get(Constants.REQUEST_ID), ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true);
    }
}
