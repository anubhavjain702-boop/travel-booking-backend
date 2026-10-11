package com.anubhav.TravelBooking.dto;


public class PaymentResponse {
	
	private Long id;
	
	private Long bookingId;
	
	private Double amount;
	
	private String paymentMethod;
	
	private String status;
	
	public PaymentResponse() {
	
	}
	
	public PaymentResponse(Long id, Long bookingId, Double amount, String paymentmethod, String status) {
		
		this.id = id;
		this.bookingId = bookingId;
		this.amount = amount;
		this.paymentMethod = paymentmethod;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
