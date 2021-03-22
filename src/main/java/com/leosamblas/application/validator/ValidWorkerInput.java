package com.leosamblas.application.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.TYPE_USE, ElementType.PARAMETER})
@Constraint(validatedBy = WorkerDataInputValidator.class)
public @interface ValidWorkerInput {

	String message() default "{ValidWorkerInput.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
