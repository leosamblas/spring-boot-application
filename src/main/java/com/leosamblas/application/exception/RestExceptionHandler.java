package com.leosamblas.application.exception;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leosamblas.application.exception.model.Erro;
import com.leosamblas.application.exception.model.Errors;
import com.leosamblas.application.util.DateUtil;

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
        
		return super.handleExceptionInternal(ex, erros, headers, status, request);
    }
	
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<Object> naoPodeChamarOLeo(CustomException cx) {		
		return new ResponseEntity<>(
				ExceptionDetails.builder()
				.status(HttpStatus.LOCKED.value())
				.details("Erro")
				.timestamp(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
				.title(cx.getMessage()).build(), HttpStatus.LOCKED);
	}
}

