/**
 * 
 */
package com.prash.sdr.model.custom.validator;

/**
 * @author f2u85i8
 *
 */
public interface CustomAnnotatonProcessor<T> {

	void init();
	
	Class<T> getSupportedClass();
	
	void validateObjectInstance(T t);
}
