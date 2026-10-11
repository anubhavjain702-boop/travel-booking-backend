package com.anubhav.TravelBooking.exception;

//import com.anubhav.TravelBooking.controller.PaymentController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
		
		return errors;
	}
	
	@ExceptionHandler(PaymentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handlePaymentNotFound(PaymentNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(PaymentAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handlePaymentAlreadyExist(PaymentAlreadyExistException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(PaymentValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handlePaymentValidation(PaymentValidationException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(BookingValidationException.class)
	public ResponseEntity<Map<String, Object>> handleBooingValidation(BookingValidationException ex){
		Map<String, Object> error = new HashMap<>();
		error.put("status", 400);
		error.put("message", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBookingNotFound(BookingNotFoundException ex){
		
		Map<String, Object> error = new HashMap<>();
		error.put("status", 404);
		error.put("message", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(HotelNotFoundException.class)
	public ResponseEntity<Map<String,Object>> handleHotelNotFound(HotelNotFoundException ex){
		
		Map<String, Object> error = new HashMap<>();
		error.put("status", 404);
		error.put("message", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	}


