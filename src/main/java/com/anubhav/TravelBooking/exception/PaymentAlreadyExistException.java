package com.anubhav.TravelBooking.exception;

public class PaymentAlreadyExistException extends RuntimeException{
	
	public PaymentAlreadyExistException(String message) {
		super(message);
	}

}
