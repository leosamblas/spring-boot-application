package com.leosamblas.application.exception.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Erro {
	protected String businessCode;
	protected String field;
    protected String message;
    protected String timestamp;
}
