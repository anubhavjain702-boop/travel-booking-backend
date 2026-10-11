package com.anubhav.TravelBooking.entity;

import java.security.MessageDigest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Booking ID is Required")
	@Positive(message = "Booking ID Mustbe Positive")
	@Column(nullable = false, unique = true)
	private Long bookingId;
	
//	@NotNull(message = "Amount Is Required")
//	@Positive(message = "Amount Must Be Greater Than Zero")
	@Column(nullable = false)
	private Double amount;
	
	@NotNull(message = "Payment Method Is required")
	@Column(nullable = false)
	private String paymentMethod;
	
//	@NotNull(message = "Payment Status Is required")
	@Column(nullable = false)
	private String status;
	
	public Payment() {
		
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
