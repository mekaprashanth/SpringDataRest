/**
 * 
 */
package com.prash.sdr.model.custom.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prash.sdr.model.Model;

/**
 * @author f2u85i8
 *
 */

@Component
public class ModelAnnotationProcessor implements CustomAnnotatonProcessor<Model>{
	
	
	List<Method> annotatedMethods;

	@Override
	@PostConstruct
	public void init() {
		annotatedMethods = Arrays.stream(getSupportedClass().getDeclaredMethods()).filter(m -> m.isAnnotationPresent(NotNullOnDemand.class)).peek(m -> m.setAccessible(true)).collect(Collectors.toList());
	}

	@Override
	public Class<Model> getSupportedClass() {
		return Model.class;
	}

	
	@Override
	public void validateObjectInstance(Model model)	{
		List<String> errors = new LinkedList<>();
		for(Method m:annotatedMethods)	{
			try {
				Class<?> clazz = m.getReturnType();
				Object val = m.invoke(model, new Object[]{});
				if(Number.class.isAssignableFrom(clazz.getClass()))	{
					if(val == null || ((Number)val).equals(0))	{
						errors.add(m.getName().substring(3)+ " cannot be null or zero");
					}
				}else if(StringUtils.isEmpty(val))	{
					errors.add(m.getName().substring(3)+ " cannot be empty");
				}
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		errors.stream().forEach(System.out::println);
	}
	
}
