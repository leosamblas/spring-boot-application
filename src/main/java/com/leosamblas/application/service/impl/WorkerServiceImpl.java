package com.leosamblas.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.leosamblas.application.controller.input.WorkerDataInput;
import com.leosamblas.application.controller.input.WorkerInput;
import com.leosamblas.application.domain.Worker;
import com.leosamblas.application.repository.WorkerRepository;
import com.leosamblas.application.service.WorkerService;
import com.leosamblas.application.validator.ValidWorkerInput;

@Service
@Validated
public class WorkerServiceImpl implements WorkerService {
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@Override
	public Worker insertWorker(@ValidWorkerInput WorkerDataInput data) {
		
		Worker worker = WorkerInput.getWorker(data.getData());
		
		return workerRepository.save(worker);
	}
}
