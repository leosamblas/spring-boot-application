package com.leosamblas.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.leosamblas.application.controller.input.WorkerDataInput;
import com.leosamblas.application.domain.Worker;
import com.leosamblas.application.validator.ValidWorkerInput;

public interface WorkerService {

	@Transactional
	Worker insertWorker(@ValidWorkerInput WorkerDataInput data);
}
