package top.easyboot.titan.handler;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import top.easyboot.titan.constant.Constants;
import top.easyboot.titan.exception.BusinessException;
import top.easyboot.titan.response.ResultCode;
import top.easyboot.titan.sign.SignEntity;
import top.easyboot.titan.sign.SignHandler;
import top.easyboot.titan.util.SignUtils;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2021-11-07 17:11
 */
@Slf4j
@Component
public abstract class SignInterceptor implements HandlerInterceptor {

    public abstract SignHandler handler();

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        try {
            return handler().checkSign(getRequestEntity(request));
        } catch (Throwable e) {
            log.error("SignInterceptor>>>>>>>>{}", e.getMessage());
            throw e;
        }
    }


    /**
     * 解析出请求信息并封装成SignEntity {@link top.easyboot.titan.sign.SignEntity}
     *
     * @param request
     * @return
     */
    public SignEntity getRequestEntity(HttpServletRequest request) {
        SignEntity signEntity = SignEntity.builder().build();
        signEntity.setMethod(request.getMethod());
        signEntity.setPath(request.getRequestURL().toString());
        //获取parameters（对应@RequestParam）
        final Map<String, Collection<String>> parameterMap = Maps.newHashMap();
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        if(!CollectionUtils.isEmpty(requestParameterMap)) {
            requestParameterMap.forEach((key, value) -> {
                List<String> values = new ArrayList<>(Arrays.asList(value));
                parameterMap.put(key, Collections.unmodifiableList(values));
            });
        }
        signEntity.setPathParams(Collections.unmodifiableMap(parameterMap));
        return signEntity;
    }

}
