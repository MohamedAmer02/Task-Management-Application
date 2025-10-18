package com.global.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.global.domain.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleExceptions(
			IllegalArgumentException exception,
			WebRequest request
			
	){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse
						.builder()
						.status(HttpStatus.BAD_REQUEST.value())
						.message(exception.getMessage())
						.details(request.getDescription(false))
						.build()
				);
	}
	

}
