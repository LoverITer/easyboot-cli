#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ${package}.exception.BusinessException;
import ${package}.request.BaseRequest;
import ${package}.response.ResultCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 14:45
 */
@Aspect
public class ApiRequestAspect {

    @Pointcut("execution(public * ${package}.controller..*.*(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object processApiRequest(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Arrays.stream(args).filter(arg -> Objects.nonNull(arg) && arg instanceof BaseRequest).forEach(arg -> {
            if (!((BaseRequest) arg).validate()) {
                throw new BusinessException(ResultCode.PARAMETER_VALIDATE_FAILED, "please check parameter:" + arg);
            }
        });
        return pjp.proceed(pjp.getArgs());
    }
}
