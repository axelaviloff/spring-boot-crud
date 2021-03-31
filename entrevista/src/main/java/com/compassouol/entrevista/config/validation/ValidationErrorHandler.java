package com.compassouol.entrevista.config.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorFormDto>> handleException(MethodArgumentNotValidException exception) {
		List<ErrorFormDto> dto = new ArrayList<ErrorFormDto>();
		List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();
		for (FieldError error : fieldErros) {
			String msg = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			ErrorFormDto erro = new ErrorFormDto(error.getField(), msg, LocalDate.now());
			dto.add(erro);
		}
		return ResponseEntity.badRequest().body(dto);
	}
}
