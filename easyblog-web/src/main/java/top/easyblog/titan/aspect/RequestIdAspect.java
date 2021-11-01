package top.easyblog.titan.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.util.IdGenerator;

/**
 * @author: frank.huang
 * @date: 2021-11-01 19:04
 */
@Aspect
public class RequestIdAspect {

    @Pointcut("@annotation(top.easyblog.titan.annotation.RequestId)")
    public void requestIdPointCut(){}


    @Around(value = "requestIdPointCut()")
    public Object handleRequestId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
          boolean existsRequestId=true;
          try{
              String requestId = MDC.get(Constants.REQUEST_ID);
              if(StringUtils.isEmpty(requestId)){
                  existsRequestId = false;
                  MDC.put(Constants.REQUEST_ID,IdGenerator.getRequestId());
              }
              return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
          }finally {
              if(!existsRequestId){
                  MDC.clear();
              }
          }
    }

}
