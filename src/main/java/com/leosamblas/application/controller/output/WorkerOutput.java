package com.leosamblas.application.controller.output;

import java.time.LocalDate;

import com.leosamblas.application.domain.Status;
import com.leosamblas.application.domain.Worker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerOutput {

	private String name;	
	private Double dailyIncome;	
	private LocalDate dataCriacao;
	private String status;
	
	public static WorkerOutput getWorkerOutput(Worker worker) {
		
		return WorkerOutput.builder()
				.name(worker.getName())
				.dailyIncome(worker.getDailyIncome())
				.dataCriacao(worker.getDataCriacao())
				.status(worker.getStatus().getStatus())
				.build();
	}
}
