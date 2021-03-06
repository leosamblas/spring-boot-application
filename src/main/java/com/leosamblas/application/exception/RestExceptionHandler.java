package com.leosamblas.application.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Objects;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leosamblas.application.exception.model.Erro;
import com.leosamblas.application.exception.model.Erros;
import com.leosamblas.application.util.DateUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private MessageSource messageSource;

	@Primary
	@Override	
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        
		Erros erros = new Erros(status.value());
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			FieldError fieldError = (FieldError) error;
			
			String field = fieldError.getField();

			String businessCode = fieldError.getDefaultMessage();
			
			Object[] parameters = Objects.isNull(fieldError.getRejectedValue()) ? null : Arrays.asList(fieldError.getRejectedValue()).toArray();

			String message = messageSource.getMessage(businessCode, parameters, businessCode, LocaleContextHolder.getLocale());
			
			Erro erro = Erro.builder()
					.businessCode(businessCode)
					.field(field)
					.message(message)
					.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.build();
				
			erros.getErros().add(erro);
		}
        
		return handleExceptionInternal(ex, erros, headers, status, request);
    }
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String details = String.format("M??todo '%s' n??o suportado", ex.getMethod());

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
				.status(String.valueOf(status.value()))
				.details(details)
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.title("M??todo requisitado n??o ?? suportado").build();
		
		return handleExceptionInternal(ex, exceptionDetails, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ex.getRootCause();
		
		if (rootCause instanceof DateTimeParseException) {
			return handleDateTimeParseException((DateTimeParseException)rootCause, headers, status, request);
		}
		
		return super.handleHttpMessageNotReadable(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			
			body = ExceptionDetails.builder()
					.status(String.valueOf(status.value()))
					.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.title("Ocorreu um erro inesperado no sistema").build();
			
		} else if (body instanceof String) {
			
			body = ExceptionDetails.builder()
					.status(String.valueOf(status.value()))
					.details((String)body)
					.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.title(status.name()).build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException (ConstraintViolationException ex) {
		
		log.error(ex.getMessage(), ex.getCause());
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String detail = String.format("A propriedade contendo o valor '%s' n??o est?? no formato correto", ex.getParsedString());
		
		ExceptionDetails exception = ExceptionDetails.builder()
				.status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
				.details(detail)
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.title("Erro ao formatar campo de data").build();
		
		return handleExceptionInternal(ex, exception, headers, status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
		
		Object[] parameters = ex.getParametros().isEmpty() ? null : ex.getParametros().toArray();
		
		String codigoErro = ex.getBusinessCode().getCode();
		
		String message = messageSource.getMessage(
				codigoErro,
				parameters, 
				"Mensagem n??o mapeada para o codigo de erro: " + ex.getBusinessCode(),
				LocaleContextHolder.getLocale());
		
		Erro erro = Erro.builder()
				.businessCode(codigoErro)
				.field(ex.getField())
				.message(message)
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.build();
		
		return new ResponseEntity<>(erro, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}

