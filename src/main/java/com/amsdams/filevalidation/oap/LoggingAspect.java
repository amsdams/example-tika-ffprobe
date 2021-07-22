package com.amsdams.filevalidation.oap;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *)|| within(@org.springframework.stereotype.Service *)|| within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
		log.info("springBeanPointcut {}");
	}

	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
	@Pointcut("within(com.amsdams.filevalidation.repository..*) || within(com.amsdams.filevalidation.service..*) || within(com.amsdams.filevalidation.web.rest..*)")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
		log.info("applicationPackagePointcut {}");

	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice.
	 * @param e         exception.
	 */
	@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception in {}() with cause = {}", joinPoint.getSignature().getName(),
				e.getCause() != null ? e.getCause() : "NULL");

	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice.
	 * @return result.
	 * @throws Throwable throws {@link IllegalArgumentException}.
	 */
	@Around("applicationPackagePointcut() && springBeanPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()));

		try {
			Object result = joinPoint.proceed();
			log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);

			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getName());
			throw e;
		}
	}
}
