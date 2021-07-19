package com.leosamblas.application.controller.input;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.leosamblas.application.domain.Status;
import com.leosamblas.application.domain.Worker;
import com.leosamblas.application.util.BusinessCode;
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
	
	@NotBlank(message = BusinessCode.CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_NOME)
	private String name;
	
	@NotNull(message = BusinessCode.CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_DAILY_INCOME)
	private Double dailyIncome;
	
	@NotNull(message = BusinessCode.CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_DATA_CRIACAO)
	private LocalDate dataCriacao;
	
	@ValidEnum(invokerClass = Status.class, ignoreCase = true, message = BusinessCode.CODIGO_ERRO_ATRIBUTO_FORA_DE_DOMINIO_STATUS, method = "getStatus")
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
