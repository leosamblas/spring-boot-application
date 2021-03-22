package com.leosamblas.application.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.leosamblas.application.controller.input.WorkerDataInput;
import com.leosamblas.application.controller.input.WorkerInput;
import com.leosamblas.application.exception.BusinessException;

@Component
public class WorkerDataInputValidator implements ConstraintValidator<ValidWorkerInput, WorkerDataInput> {

	@Override
	public boolean isValid(WorkerDataInput value, ConstraintValidatorContext context) {

		WorkerInput input = value.getData();

		if (input.getName().equals("Leonardo Samblas") && input.getDailyIncome() > 1200.00) {
			throw new BusinessException("422.101", input.getName(), input.getDailyIncome().toString());
		}
		
		if(!input.getDataCriacao().isEqual(LocalDate.now())) {
			throw new BusinessException("422.102", input.getDataCriacao().toString());
		}

		return true;
	}

}
