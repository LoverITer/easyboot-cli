package top.easyboot.titan.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.easyboot.titan.annotation.ResponseWrapper;
import top.easyboot.titan.response.BaseResponse;

import java.util.Objects;

/**
 * 响应增强类
 *
 * @author: frank.huang
 * @date: 2021-11-01 18:28
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice, Ordered {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getAnnotatedElement().isAnnotationPresent(ResponseWrapper.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class selectedClassType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.nonNull(body)) {
            if(body instanceof BaseResponse) {
                log.info("Writing "+ JSON.toJSON(body));
                return body;
            }
            // 非JSON格式body需要组装成BaseResponse
            BaseResponse<Object> responseBody = BaseResponse.ok(body);
            log.info("Writing "+ JSON.toJSONString(responseBody));
            return responseBody;
        }
        return BaseResponse.ok(null);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
