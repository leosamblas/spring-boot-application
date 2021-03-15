package com.leosamblas.application.validator.enumvalidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EnumConstraintValidator.class })
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface EnumValidator {
	
	abstract String message() default "Campo fora do domínio";

	abstract Class<?>[] groups() default {};

	abstract Class<? extends Payload>[] payload() default {};

	abstract Class<?> invokerClass();
	
	abstract String method() default "getStatus";
	
	abstract boolean ignoreCase() default false;
}
