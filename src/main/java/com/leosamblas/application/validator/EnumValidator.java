package com.leosamblas.application.validator;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

	private ValidEnum annotation;

	@Override
	public void initialize(ValidEnum constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if (value == null) {
			return false;
		}

		Object[] objects = annotation.invokerClass().getEnumConstants();
		
		try {
			
			Method method = annotation.invokerClass().getMethod(annotation.method());
			
			for (Object o : objects) {
				if (annotation.ignoreCase() && ((String) value).equalsIgnoreCase((String)method.invoke(o)) 
						|| value.equals(method.invoke(o))) {
					return true;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return false;
	}
}
