package com.example.spring.Security.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions across the application.
 */
@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<Object> handleServiceException(ServiceException ex) {
		// Logging the exception details
		log.error("Service Exception occurred: { Message: " + ex.getMessage() + " }");
		// Creating error message object
		ErrorMessage errorMessage = new ErrorMessage(ex.getStatus().value(), ex.getMessage());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), ex.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		// Creating a map to hold field errors
		Map<String, String> errors = new HashMap<>();
		// Extracting field errors and populating the map
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		// Logging field validation errors
		errors.forEach((key, val) -> log.info("Field validation error - {} :: {}", key, val));
		// Creating error message object for validation errors
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Validation failed");
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
		// Logging illegal argument exceptions
		log.error("Illegal Argument Exception: " + ex.getMessage());
		// Creating error message object for illegal argument exceptions
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleGeneralException(Exception ex) {
		// Logging general exceptions
		log.error("General Exception occurred: { Message: " + ex.getMessage() + " }");
		// Creating error message object for general exceptions
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
