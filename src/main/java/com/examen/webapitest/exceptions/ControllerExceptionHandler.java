package com.examen.webapitest.exceptions;

import com.examen.webapitest.model.MensajeError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public MensajeError ValidationExceptionHandler(MethodArgumentNotValidException exception) {
		MensajeError errorMessage = new MensajeError();
		ObjectError oError = exception.getAllErrors().get(0);
		
		errorMessage.setMensaje(String.format("campo %s  %s", exception.getFieldError().getField(), oError.getDefaultMessage()));
		return errorMessage;
	}
	
	@ExceptionHandler(BussinesException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public MensajeError validBusinessLogicException(BussinesException nisumException) {
		MensajeError errorMessage = new MensajeError();
		errorMessage.setMensaje(nisumException.getMessage());
		return errorMessage;
	}
	
	@ExceptionHandler(HttpServerErrorException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public MensajeError ValidNisumException(HttpServerErrorException serverError) {
		MensajeError errorMessage = new MensajeError();
		errorMessage.setMensaje(serverError.getMessage());
		serverError.printStackTrace();
		return errorMessage;
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public MensajeError validateSqlException(DataIntegrityViolationException exception) {
		MensajeError error = new MensajeError();
		ConstraintViolationException vException = (ConstraintViolationException) exception.getCause();
		if(vException.getConstraintName().indexOf("UNIQUE_EMAIL") >= 0) {
			error.setMensaje("email ya se encuentra registrado.");
		}else {
			error.setMensaje(exception.getMessage());
		}
		return error;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public MensajeError ValidGenericException(Exception serverError) {
		MensajeError errorMessage = new MensajeError();
		errorMessage.setMensaje(serverError.getMessage());
		serverError.printStackTrace();
		return errorMessage;
	}
	
}
