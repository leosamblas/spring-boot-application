package com.leosamblas.application.util;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BusinessCodeMapper {

	@Autowired
	private MessageSource messageSource;

	public static final BusinessCode VALOR_DAILY_INCOME_NAO_PODE_SER_SUPERIOR = new BusinessCode(BusinessCode.VALOR_DAILY_INCOME_NAO_PODE_SER_SUPERIOR);
	public static final BusinessCode DATA_CRIACAO_NAO_PODE_SER_DATA_DE_HOJE = new BusinessCode(BusinessCode.DATA_CRIACAO_NAO_PODE_SER_DATA_DE_HOJE);
	
	public static final BusinessCode ATRIBUTO_OBRIGATORIO_NOME = new BusinessCode(BusinessCode.CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_NOME);

	public String getMessageFromBusinessCode(BusinessCode businessCode, String... parameters) {
		return messageSource.getMessage(
				businessCode.getCode(), 
				Objects.isNull(parameters) ? null : parameters,
				LocaleContextHolder.getLocale());
	}
}
