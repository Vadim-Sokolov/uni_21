package thyme.controller;

import org.springframework.http.HttpStatus;

public class ExceptionInformationDto {

	private final String message;
	private final HttpStatus httpStatus;

	public ExceptionInformationDto(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
