package com.leosamblas.application.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.leosamblas.application.domain.converter.StatusConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker")
public class Worker implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NUM_ID_WORKER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numIdWorker;
	
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DAILY_INCOME")
	private Double dailyIncome;	
	
	@Column(name = "NUM_ID_STATUS")
	@Convert(converter = StatusConverter.class)
	private Status status;
	
	@NotNull
	@Column(name = "DAT_CRIACAO")	
	private LocalDate dataCriacao;
}