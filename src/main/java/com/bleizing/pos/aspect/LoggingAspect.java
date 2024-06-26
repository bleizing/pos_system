package com.bleizing.pos.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
	@Around("@annotation(com.bleizing.pos.annotation.Logged)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Start {}, args = {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
	    Object proceed = joinPoint.proceed();
		log.info("End {}", joinPoint.getSignature().getName());
		return proceed;
	}
	
	@Before("execution(* com.bleizing.pos..*..*(..))")
    public void before(JoinPoint joinPoint) {
//		log.info("Before {}", joinPoint.getSignature().getName());
    }
	
	@After("execution(* com.bleizing.pos..*..*(..))")
    public void after(JoinPoint joinPoint) {
//		log.info("After {}", joinPoint.getSignature().getName());
    }
}