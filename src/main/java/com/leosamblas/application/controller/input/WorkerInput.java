package com.leosamblas.application.controller.input;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.leosamblas.application.domain.Status;
import com.leosamblas.application.domain.Worker;
import com.leosamblas.application.validator.EnumValidator;
import com.leosamblas.application.validator.ValidEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerInput {
	
	@NotBlank
	private String name;
	
	@NotNull
	private Double dailyIncome;
	
	@NotNull
	private LocalDate dataCriacao;
	
	@ValidEnum(invokerClass = Status.class, ignoreCase = true, message = "{workerDataInput.data.status}", method = "getStatus")
	private String status;
	
	public static Worker getWorker(WorkerInput input) {
		
		return Worker.builder()
				.name(input.getName())
				.dailyIncome(input.getDailyIncome())
				.dataCriacao(input.getDataCriacao())
				.status(Status.fromStatus(input.getStatus()))
				.build();
	}
}
