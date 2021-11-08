package top.easyboot.titan.aspect;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;
import top.easyboot.titan.config.request.BufferedHttpServletRequest;
import top.easyboot.titan.constant.Constants;
import top.easyboot.titan.exception.BusinessException;
import top.easyboot.titan.response.ResultCode;
import top.easyboot.titan.util.SignUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2021-11-07 17:11
 */
@Aspect
@Component
public class SignatureAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    @Around("execution(* top.easyboot.titan.controller.*.*(..)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping))"
    )
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        try {
            this.checkSign();
            return pjp.proceed();
        } catch (Throwable e) {
            LOGGER.error("SignatureAspect>>>>>>>>", e);
            throw e;
        }
    }

    private void checkSign() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String oldSign = request.getHeader(Constants.SIGN);
        if (StringUtils.isEmpty(oldSign)) {
            throw new BusinessException(ResultCode.SIGN_NOT_FOUND, "sign fail: sign not found");
        }
        //获取body（对应@RequestBody）
        String body = null;
        if (request instanceof BufferedHttpServletRequest) {
            body = IOUtils.toString(request.getInputStream(), Constants.DEFAULT_CHARSET);
        }

        //获取parameters（对应@RequestParam）
        Map<String, String[]> params = null;
        if (!CollectionUtils.isEmpty(request.getParameterMap())) {
            params = request.getParameterMap();
        }

        //获取path variable（对应@PathVariable）
        String[] paths = null;
        ServletWebRequest webRequest = new ServletWebRequest(request, null);
        Object uriTemplateVars = webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if(Objects.nonNull(uriTemplateVars) && uriTemplateVars instanceof Map){
            Map<String, String> uriTemplateMap=(Map<String, String>)uriTemplateVars;
            if (!CollectionUtils.isEmpty(uriTemplateMap)) {
                paths = uriTemplateMap.values().toArray(new String[]{});
            }
        }


        String newSign = SignUtils.sign(body, params, paths);
        if (!newSign.equals(oldSign)) {
            throw new BusinessException(ResultCode.SIGN_FAIL, "sign fail: sign illegal");
        }
    }

}
