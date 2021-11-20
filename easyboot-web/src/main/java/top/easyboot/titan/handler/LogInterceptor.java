package top.easyboot.titan.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2021-11-20 19:58
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Value("${spring.custom.enable-logging-request-details:true}")
    private Boolean enableLoggingRequestDetails;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String params;
        if (isEnableLoggingRequestDetails()) {
            params = JSON.toJSON( request.getParameterMap()).toString();
        } else {
            params = request.getParameterMap().isEmpty() ? "" : "masked";
        }
        String queryString = request.getQueryString();
        String queryClause = StringUtils.hasLength(queryString) ? "?" + queryString : "";
        String message = request.getMethod() + " " + getRequestUri(request) + queryClause + ", parameters={" + params + "}";
        log.info(message);
        return true;
    }



    private boolean isEnableLoggingRequestDetails(){
        return enableLoggingRequestDetails;
    }

    private static String getRequestUri(HttpServletRequest request) {
        String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null) {
            uri = request.getRequestURL().toString();
        }

        return uri;
    }

}
