package top.easyboot.titan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author: frank.huang
 * @date: 2021-12-03 22:18
 */
@Slf4j
@Aspect
@Component
public class MethodExecuteTimeAspect {


    @Around("execution(* top.easyboot.titan.service.*.*(..)) ||" +
            "execution(* top.easyboot..titan.controller.*.*(..)) ||" +
            "execution(* top.easyboot.titan.feign.client.*.*(..))")
    public Object logCostTime(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = null;
        try {
            proceed = pjp.proceed(pjp.getArgs());
            stopWatch.stop();
            log.info("The method {} cost time: {} ms",signature.getName(),stopWatch.getTotalTimeMillis());
        } catch (Throwable e) {
            log.info("An exception occurred when compute the cost time of method {}: [message={},cause={},stack={}]",
                    signature.getName(),e.getMessage(),e.getCause(),e.getStackTrace());
        }
        return proceed;
    }


}
