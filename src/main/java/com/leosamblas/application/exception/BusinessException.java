package com.leosamblas.application.exception;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.util.ObjectUtils;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String businessCode;
	private final Set<String> parametros = new LinkedHashSet<>();
	
	public BusinessException(String businessCode) {	
		this.businessCode = businessCode;
	}
	
	public BusinessException(String businessCode, String... parametros) {
		setParametro(parametros);
		this.businessCode = businessCode;
	}
	
	public BusinessException(String businessCode, Throwable throwable, String... parametros) {
		super(throwable);
		setParametro(parametros);
		this.businessCode = businessCode;
	}

	public String getBusinessCode() {
		return this.businessCode;
	}

	public Set<String> getParametros() {
		return this.parametros;
	}
	
	private void setParametro(String... parametros) {
		if (!ObjectUtils.isEmpty(parametros)) {
			for (String param : parametros) {
				this.parametros.add(param.trim());
			}
		}
	}
	
}
