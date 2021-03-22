package com.leosamblas.application.exception.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Erros {
	
	private Integer status;
	private List<Erro> erros = new ArrayList<>();
	
	public Erros (Integer status) {
		this.status = status;
	}
}
