/**
 * 
 */
package com.prash.sdr.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author f2u85i8
 *
 */

@Aspect
public class LoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Before("execution(* com.prash.sdr.spring.controller..*(..))")
	public void logBeforeController(final JoinPoint point) {
		this.logger.info("Entering-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName() );
	}

	@After("execution(* com.prash.sdr.spring.controller..*(..))")
	public void logAfterController(final JoinPoint point) {
		this.logger.info("Exiting-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName()  );
	}


	@Before("execution(* com.prash.sdr.model..*(..))")
	public void logBeforeDao(final JoinPoint point) {
		this.logger.info("Entering-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName() );
	}

	@After("execution(* com.prash.sdr.model..*(..))")
	public void logAfterDao(final JoinPoint point) {
		this.logger.info("Exiting-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName()  );
	}
	
	@Before("execution(* com.prash.sdr.repository..*(..))")
	public void logBeforeRepository(final JoinPoint point) {
		this.logger.info("Entering-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName() );
	}

	@After("execution(* com.prash.sdr.repository..*(..))")
	public void logAfterRepository(final JoinPoint point) {
		this.logger.info("Exiting-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName()  );
	}

	@Before("execution(* com.prash.sdr.service..*(..))")
	public void logBeforeService(final JoinPoint point) {
		this.logger.info("Entering-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName() );
	}

	@After("execution(* com.prash.sdr.service..*(..))")
	public void logAfterService(final JoinPoint point) {
		this.logger.info("Exiting-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName()  );
	}

	@Before("execution(* com.prash.sdr.delegate..*(..))")
	public void logBeforeDelegate(final JoinPoint point) {
		this.logger.info("Entering-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName() );
	}

	@After("execution(* com.prash.sdr.delegate..*(..))")
	public void logAfterDelegate(final JoinPoint point) {
		this.logger.info("Exiting-->"+point.getSignature().getName()+" of "+point.getTarget().getClass().getName()  );
	}

}
