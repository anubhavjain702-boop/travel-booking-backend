package com.anubhav.TravelBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anubhav.TravelBooking.dto.PaymentResponse;
import com.anubhav.TravelBooking.entity.Payment;
import com.anubhav.TravelBooking.service.PaymentService;

import jakarta.validation.Valid;

@RestController
public class PaymentController {
	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/api/payments")
	public PaymentResponse makePayment(@Valid @RequestBody Payment payment) {
		return paymentService.makePayment(payment);
	}
	
	@GetMapping("/api/payments")
	public List<PaymentResponse> getAllpayments(){
		return paymentService.getAllPayments();
	}
	
	@GetMapping("/api/payments/{id}")
	public PaymentResponse getPaymentById(@PathVariable Long id) {
		return paymentService.getPaymentById(id);
	}

}
