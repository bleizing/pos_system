package com.bleizing.pos.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
	
//	@Around("@annotation(com.bleizing.pos.annotation.Logged)")
//	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//		log.info("Start {} -> args = {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
//	    Object proceed = joinPoint.proceed();
//		log.info("End {}", joinPoint.getSignature().getName());
//		return proceed;
//	}
	
//	@Before("execution(* com.bleizing.pos..*..*(..))")
//    public void before(JoinPoint joinPoint) {
//		log.info("Before {}", joinPoint.getSignature().getName());
//    }
	
//	@After("execution(* com.bleizing.pos..*..*(..))")
//    public void after(JoinPoint joinPoint) {
//		log.info("After {}", joinPoint.getSignature().getName());
//    }
	

	@Pointcut("@annotation(com.bleizing.pos.annotation.Logged)")
	private void logged() {
	}
	
	@Before(value = "logged()")
    public void logBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
	    String methodName = joinPoint.getSignature().getName();
	    log.info("Before {}() -> {}", methodName, Arrays.toString(args));
    }
	
	@After(value = "logged()")
    public void logAfter(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
	    String methodName = joinPoint.getSignature().getName();
	    log.info("After {}() -> {}", methodName, Arrays.toString(args));
    }
	
	@Around(value = "logged()")
	public Object loggedAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Start {}() -> args = {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
	    Object proceed = joinPoint.proceed();
		log.info("End {}()", joinPoint.getSignature().getName());
		return proceed;
	}
	
	@AfterThrowing(pointcut = "logged()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Throwable exception) {
	    String methodName = joinPoint.getSignature().getName();
	    log.error("AfterThrowing {}() -> {}", methodName, exception.getMessage());
	}
	
	@AfterReturning(value = "logged()", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
	    String methodName = joinPoint.getSignature().getName();
	    log.info("AfterReturning {}() -> {}", methodName, result);
	}
}