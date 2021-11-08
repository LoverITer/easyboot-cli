package top.easyboot.titan.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.easyboot.titan.config.request.BufferedHttpServletRequest;
import top.easyboot.titan.constant.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Order(1)
@Component
public class RequestCachingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCachingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        if (isFirstRequest && !(request instanceof BufferedHttpServletRequest)) {
            requestToUse = new BufferedHttpServletRequest(request, 1024);
        }
        try {
            filterChain.doFilter(requestToUse, response);
        } catch (Exception e) {
            LOGGER.error("RequestCachingFilter>>>>>>>>>", e);
        } finally {
            this.logRequestInfo(requestToUse);
            if (requestToUse instanceof BufferedHttpServletRequest) {
                ((BufferedHttpServletRequest) requestToUse).release();
            }
        }
    }

    private void logRequestInfo(HttpServletRequest request) {
        String body = StringUtils.EMPTY;
        try {
            if (request instanceof BufferedHttpServletRequest) {
                body = IOUtils.toString(request.getInputStream(), Constants.DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            LOGGER.error("Get Request-Info fail:{}", e.getMessage());
        }

        JSONObject headerParam = new JSONObject();
        JSONObject headers = new JSONObject();
        Collections.list(request.getHeaderNames()).forEach(name -> headers.put(name, request.getHeader(name)));
        headerParam.put("headers", headers);
        headerParam.put("parameters", request.getParameterMap());
        headerParam.put("body", body);
        headerParam.put("remote-user", request.getRemoteUser());
        headerParam.put("remote-addr", request.getRemoteAddr());
        headerParam.put("remote-host", request.getRemoteHost());
        headerParam.put("remote-port", request.getRemotePort());
        headerParam.put("uri", request.getRequestURI());
        headerParam.put("url", request.getRequestURL());
        headerParam.put("servlet-path", request.getServletPath());
        headerParam.put("method", request.getMethod());
        headerParam.put("query", request.getQueryString());
        headerParam.put("path-info", request.getPathInfo());
        headerParam.put("context-path", request.getContextPath());

        LOGGER.info("Request-Info: " + JSON.toJSONString(headerParam, SerializerFeature.PrettyFormat));
    }

}