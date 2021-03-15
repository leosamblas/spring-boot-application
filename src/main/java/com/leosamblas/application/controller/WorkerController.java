package com.leosamblas.application.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leosamblas.application.controller.input.WorkerDataInput;
import com.leosamblas.application.controller.input.WorkerInput;
import com.leosamblas.application.controller.output.WorkerOutput;
import com.leosamblas.application.domain.Status;
import com.leosamblas.application.domain.Worker;
import com.leosamblas.application.exception.CustomException;
import com.leosamblas.application.repository.WorkerRepository;

@RestController
@RequestMapping("workers")
public class WorkerController {

	@Autowired
	private WorkerRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Worker>> findAll() {
		List<Worker> list = repository.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<WorkerOutput> findById(@PathVariable Long id) {
			
		Optional<Worker> obj = repository.findById(id);
				
		if(obj.isPresent()) {
			return new ResponseEntity<>(WorkerOutput.getWorkerOutput(obj.get()), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<WorkerOutput> insert(@RequestBody @Valid WorkerDataInput data) {
		
		Worker worker = WorkerInput.getWorker(data.getData());
		
		if(worker.getName().equals("LEO")) {
			throw new CustomException("Voce não pode chamar o LEO");
		}
		
		repository.save(worker);		
		
		return new ResponseEntity<>(WorkerOutput.getWorkerOutput(worker), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid WorkerDataInput input) {
		
		Optional<Worker> workerFromDb = repository.findById(id);
		
		if(workerFromDb.isPresent()) {			
			
			Worker worker = Worker.builder()
				.numIdWorker(workerFromDb.get().getNumIdWorker())
				.name(input.getData().getName())
				.dailyIncome(input.getData().getDailyIncome())
				.status(Status.fromStatus(input.getData().getStatus()))
				.build();
			
			repository.save(worker);	
			
			return new ResponseEntity<>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
