package com.dnb.customerservice.advice;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dnb.customerservice.exception.IdNotFoundException;
import com.dnb.customerservice.exception.InvalidContactNumberException;

@ControllerAdvice
public class AppAdvice {
	
	
//	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "invalid id provided")
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> InvalidIdExceptionHandler (IdNotFoundException e){

		Map<String, String> map = new HashMap<>();

		map.put("message", e.getMessage());

		map.put("HttpStatus", HttpStatus.NOT_FOUND + "");

		return new ResponseEntity(map,HttpStatus.NOT_FOUND);

	}
	
	
	@ExceptionHandler(InvalidContactNumberException.class)
	public ResponseEntity<?> InvalidIdExceptionHandler (InvalidContactNumberException e){

		Map<String, String> map = new HashMap<>();

		map.put("message", e.getMessage());

		map.put("HttpStatus", HttpStatus.NOT_FOUND + "");

		return new ResponseEntity(map,HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleException(HttpRequestMethodNotSupportedException e){
		
		String provided = e.getMethod();
		List<String> supported = List.of(e.getSupportedMethods());
		
		String error = provided 
				+ " is not one of the supported Http Method ("
				+ String.join(",", supported)
				+ ")";
				
				
		Map<String, String > errorResponse = Map.of(
				"error", error,
				"message", e.getLocalizedMessage(),
				"status", HttpStatus.METHOD_NOT_ALLOWED.toString());
				
		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
	
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatusCode status, HttpRequest request) {
		
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp" , LocalDateTime.now());
		responseBody.put("status",  status.value());
		
		List<String> errors = e.getBindingResult().getFieldErrors().stream()
				.map(x -> x.getField())
				.collect(Collectors.toList());
		
		//array of fields or message
		// should be stored into a map where k : fieldName, v : value.
		
		Map<String, String> resultMap = e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		
		
		
		responseBody.put("error", resultMap);
		
		return new ResponseEntity<>(responseBody, headers, status);
	}
}
