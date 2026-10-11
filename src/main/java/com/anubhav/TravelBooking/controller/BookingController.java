package com.anubhav.TravelBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anubhav.TravelBooking.entity.Booking;
import com.anubhav.TravelBooking.service.BookingService;

import jakarta.validation.Valid;

@RestController
public class BookingController {
	
	private final BookingService bookingService;
	
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@PostMapping("/api/bookings")
	public Booking createBooking(@Valid@RequestBody Booking booking) {
		return bookingService.createBooking(booking);
	}
	
	@GetMapping("/api/bookings")
	public List<Booking> getAllBookings(){
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/api/bookings/{id}")
	public Booking getBookingById(@PathVariable Long id) {
		return bookingService.getBookingById(id);
		
	}
	
	@DeleteMapping("/api/bookings/{id}")
	public Booking cancelBooking(@PathVariable Long id) {
		return bookingService.cancelBooking(id);
	}

}
