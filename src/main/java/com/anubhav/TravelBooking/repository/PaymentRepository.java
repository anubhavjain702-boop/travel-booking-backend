package com.anubhav.TravelBooking.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.TravelBooking.entity.Payment;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	List<Payment> findByBookingId(Long bookingId);

}
