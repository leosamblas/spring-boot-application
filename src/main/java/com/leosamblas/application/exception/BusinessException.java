package com.leosamblas.application.exception;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.util.ObjectUtils;

import com.leosamblas.application.util.BusinessCode;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final BusinessCode code;
	private final String field;
	private final Set<String> parametros = new LinkedHashSet<>();

	public BusinessException(BusinessCode code, String field) {
		this.code = code;
		this.field = field;
	}

	public BusinessException(BusinessCode code, String field, String... parametros) {
		setParametro(parametros);
		this.code = code;
		this.field = field;
	}

	public BusinessException(BusinessCode code, String field, Throwable throwable, String... parametros) {
		super(throwable);
		setParametro(parametros);
		this.code = code;
		this.field = field;
	}

	public BusinessCode getBusinessCode() {
		return this.code;
	}

	public String getField() {
		return this.field;
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
