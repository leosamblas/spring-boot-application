package com.leosamblas.application.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.leosamblas.application.controller.input.WorkerDataInput;
import com.leosamblas.application.controller.input.WorkerInput;
import com.leosamblas.application.exception.BusinessException;
import com.leosamblas.application.util.BusinessCodeMapper;

@Component
public class WorkerDataInputValidator implements ConstraintValidator<ValidWorkerInput, WorkerDataInput> {

	@Override
	public boolean isValid(WorkerDataInput value, ConstraintValidatorContext context) {

		WorkerInput input = value.getData();		
		
		value.getClass().getFields();

		if (input.getName().equals("Leonardo Samblas") && input.getDailyIncome() > 1200.00) {
			throw new BusinessException(BusinessCodeMapper.VALOR_DAILY_INCOME_NAO_PODE_SER_SUPERIOR, null, input.getDailyIncome().toString());
		}
		
		if(!input.getDataCriacao().isEqual(LocalDate.now())) {
			throw new BusinessException(BusinessCodeMapper.DATA_CRIACAO_NAO_PODE_SER_DATA_DE_HOJE, input.getDataCriacao().toString());
		}

		return true;
	}

}
