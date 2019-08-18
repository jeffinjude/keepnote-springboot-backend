package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Annotate this class with @Aspect and @Component */

@Aspect
@Component
public class LoggingAspect {
	/**
	 * 
	 */

	private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * 
	 * @param joinPoint
	 */
	@Before("within(com.stackroute.keepnote..*)")
	public void beforeLog(JoinPoint joinPoint) {
		logger.info("Before Aspects in : " + joinPoint.getSignature().getName());
	}

	/**
	 * 
	 * @param joinPoint
	 */
	@After("within(com.stackroute.keepnote..*)")
	public void afterLog(JoinPoint joinPoint) {
		logger.info("After Aspects in : " + joinPoint.getSignature().getName());
	}

	/**
	 * 
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "within(com.stackroute.keepnote..*)", returning = "result")
	public void afterReturningLog(JoinPoint joinPoint, Object result) {
		logger.info("After Return Aspects in : " + joinPoint.getSignature().getName() + " : " + result);
	}

	/**
	 * 
	 * @param joinPoint
	 * @param error
	 */
	@AfterThrowing(pointcut = "within(com.stackroute.keepnote..*)", throwing = "error")
	public void afterThrowLog(JoinPoint joinPoint, Throwable error) {
		logger.info("After Throw Aspects in : " + joinPoint.getSignature().getName() + " : " + error);
	}

}
