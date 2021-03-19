package com.leosamblas.application.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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
import com.leosamblas.application.exception.model.Errors;
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
        
		Errors erros = new Errors(status.value());
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			FieldError fieldError = (FieldError) error;
			
			String field = fieldError.getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			Erro erro = Erro.builder()
					.field(field)
					.message(mensagem)
					.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.build();
				
			erros.getErros().add(erro);
		}
        
		return handleExceptionInternal(ex, erros, headers, status, request);
    }
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String details = String.format("Método '%s' não suportado", ex.getMethod());

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
				.status(status.value())
				.details(details)
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.title("Método requisitado não é suportado").build();
		
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
					.status(status.value())
					.details("Ocorreu um erro inesperado no sistema")
					.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.title(status.name()).build();
			
		} else if (body instanceof String) {
			
			body = ExceptionDetails.builder()
					.status(status.value())
					.details((String)body)
					.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.title(status.name()).build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> naoPodeChamarOLeo(CustomException cx) {		
		return new ResponseEntity<>(
				ExceptionDetails.builder()
				.status(HttpStatus.LOCKED.value())
				.details("Erro")
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.title(cx.getMessage()).build(), HttpStatus.LOCKED);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException (ConstraintViolationException ex) {
		
		log.error(ex.getMessage(), ex.getCause());
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String detail = String.format("A propriedade contendo o valor '%s' não está no formato correto", ex.getParsedString());
		
		ExceptionDetails exception = ExceptionDetails.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.details(detail)
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.title("Erro ao formatar campo de data").build();
		
		return handleExceptionInternal(ex, exception, headers, status, request);
	}
}

