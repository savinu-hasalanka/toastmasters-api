package com.example.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.example.app.controller.*.*(..))")
    public void forControllerPackage() {}

    @Pointcut("execution(* com.example.app.service.*.*(..))")
    public void forServicePackage() {}

    @Pointcut("forControllerPackage() || forServicePackage()")
    public void forApiFlow() {}

    @Before("forApiFlow()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().toShortString();

        logger.info("===> @Before calling method: " + methodName);

        for (Object arg : args) {
            logger.info("===> argument: " + arg);
        }

    }

    @AfterReturning(
            pointcut = "forApiFlow()",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();

        logger.info("===> @AfterReturn from method: " + methodName);
        logger.info("===> result: " + result);
    }
}
