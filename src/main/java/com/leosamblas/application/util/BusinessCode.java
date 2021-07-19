package com.leosamblas.application.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCode implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;

	public static final String VALOR_DAILY_INCOME_NAO_PODE_SER_SUPERIOR = "422.100";
	public static final String DATA_CRIACAO_NAO_PODE_SER_DATA_DE_HOJE = "422.102";
	public static final String CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_NOME = "422.103";
	public static final String CODIGO_ERRO_ATRIBUTO_FORA_DE_DOMINIO_STATUS = "422.104";
	public static final String CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_DATA_CRIACAO = "422.105";
	public static final String CODIGO_ERRO_ATRIBUTO_OBRIGATORIO_DAILY_INCOME = "422.106";
	public static final String CODIGO_ERRO_ATRIBUTO_DATA_CRIACAO_MAIOR_QUE_HOJE = "422.107";
}
