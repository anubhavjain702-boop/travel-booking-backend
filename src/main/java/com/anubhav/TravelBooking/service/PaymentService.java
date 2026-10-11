package com.anubhav.TravelBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anubhav.TravelBooking.dto.PaymentResponse;
import com.anubhav.TravelBooking.entity.Booking;
import com.anubhav.TravelBooking.entity.Payment;
import com.anubhav.TravelBooking.exception.BookingNotFoundException;
import com.anubhav.TravelBooking.exception.PaymentAlreadyExistException;
import com.anubhav.TravelBooking.exception.PaymentNotFoundException;
import com.anubhav.TravelBooking.exception.PaymentValidationException;
import com.anubhav.TravelBooking.repository.BookingRepository;
import com.anubhav.TravelBooking.repository.PaymentRepository;



@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final BookingRepository bookingRepository;
	
	public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
		this.paymentRepository = paymentRepository;
		this.bookingRepository = bookingRepository;
	}
	
	@Transactional
	public PaymentResponse makePayment(Payment payment) {
		
		Booking booking = bookingRepository.findById(payment.getBookingId()).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
		
		if("CANCELLED".equalsIgnoreCase(booking.getStatus()) || "cancelled".equalsIgnoreCase(booking.getStatus())) {
			throw new PaymentValidationException("can not payment for a cancelled booking");
		}
		
		if(!paymentRepository.findByBookingId(payment.getBookingId()).isEmpty()){
			throw new PaymentAlreadyExistException("Payment Already exist for this booking");
		}
		
		String method = payment.getPaymentMethod();
		
		if(method == null || !(method.equalsIgnoreCase("UPI") || method.equalsIgnoreCase("CARD") || method.equalsIgnoreCase("NET_BANKING"))) {
			throw new PaymentValidationException("Invalid Payment Method. Allowwed: UPI, CARD, NET_BANKING");
		}
		
		payment.setPaymentMethod(method.toUpperCase());
		payment.setAmount(booking.getTotalAmount());
		payment.setStatus("success");
		
		booking.setStatus("CONFIRMED");
		bookingRepository.save(booking);
		
		Payment savedPayment = paymentRepository.save(payment);
		
		return new PaymentResponse(
				savedPayment.getId(),
				savedPayment.getBookingId(),
				savedPayment.getAmount(),
				savedPayment.getPaymentMethod(),
				savedPayment.getStatus()
				);
	}
	
	public List<PaymentResponse> getAllPayments(){
		return paymentRepository.findAll().stream()
				.map(payment -> new PaymentResponse(
						payment.getId(),
						payment.getBookingId(),
						payment.getAmount(),
						payment.getPaymentMethod(),
						payment.getStatus()
				)).toList();
	}
	
	public PaymentResponse getPaymentById(Long id) {
		Payment payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("payment not found"));	
		
		return new PaymentResponse(
				payment.getId(),
				payment.getBookingId(),
				payment.getAmount(),
				payment.getPaymentMethod(),
				payment.getStatus()
				);
	}

}
