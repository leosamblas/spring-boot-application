package com.leosamblas.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leosamblas.application.domain.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
	
}
