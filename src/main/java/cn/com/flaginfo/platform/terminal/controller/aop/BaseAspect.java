package cn.com.flaginfo.platform.terminal.controller.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Created by liang_zhang on 2017/12/14.
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class BaseAspect {
    private static final Logger log = LoggerFactory.getLogger(BaseAspect.class);


    public BaseAspect(){
        log.info("aspect init");
    }


    @Pointcut("execution(* cn.com.flaginfo.platform.terminal.controller.TestController.*(..))")
    public void pointcut(){
    }


//    @Before("pointcut()")
//    public void beforeRequest(JoinPoint jp){
//        String methodName = jp.getSignature().getName();
//        Object[] args = jp.getArgs();
//        log.info("Before request,method:[{}],args:[{}]",methodName, JSONObject.toJSONString(args));
//    }

    @Around("pointcut()")
    public Object aroundRequest(ProceedingJoinPoint pjp){
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        long startTime = System.currentTimeMillis();
        String methodInfo= String.format("method:[%s],args:[%s]",methodName, JSONObject.toJSONString(args));
        log.info("Before request url,{}", methodInfo);
        Object result = null;
        try {
            result = pjp.proceed(args);
        } catch (Throwable ex) {
            ex.printStackTrace();
//            for (Throwable e : ExceptionUtils.getThrowableList(ex)) {
//                String msg = e.getMessage();
//                if (StringUtils.isNotBlank(msg)) {
//                    throw new InvalidStateException(msg);
//                }
//            }
        }
        long tokeTime = System.currentTimeMillis() - startTime;
        log.info("After request,{},完成, 花费总时间：[{}] ms \n request result:[{}]", methodInfo, tokeTime, result);
        //结果转为json字符串
        return JSONObject.toJSONString(result);
    }


//    @AfterReturning(value = "pointcut()",returning = "returnValue")
//    public void afterRequest(JoinPoint jp,Object returnValue) {
//        log.info("After request, return value is[{}]",returnValue);
//        Map<String,Object> map=new HashMap<>(5);
//        map.put("data",returnValue);
//        map.put("code",200L);
//        map.put("msg","request sucess");
//    }


}
