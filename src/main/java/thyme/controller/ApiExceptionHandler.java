package thyme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import thyme.service.ServiceException;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = { ServiceException.class })
	public ResponseEntity<Object> handleServiceException(ServiceException serviceException) {
		ExceptionInformationDto dto = new ExceptionInformationDto(serviceException.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
