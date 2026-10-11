package com.anubhav.TravelBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anubhav.TravelBooking.entity.Booking;
import com.anubhav.TravelBooking.entity.Hotel;
import com.anubhav.TravelBooking.exception.BookingNotFoundException;
import com.anubhav.TravelBooking.exception.BookingValidationException;
import com.anubhav.TravelBooking.exception.HotelNotFoundException;
import com.anubhav.TravelBooking.repository.BookingRepository;
import com.anubhav.TravelBooking.repository.HotelRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {
	
	private final BookingRepository bookingRepository;
	private final HotelRepository hotelRepository;
	
	
	
	public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository) {
		this.bookingRepository = bookingRepository;
		this.hotelRepository = hotelRepository;
	}
	
	@Transactional
	public Booking createBooking(Booking booking) {
		
		Long hotelId = booking.getHotel().getId();
		
		Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException("Hotel Not Found"));
		
		if(booking.getCheckIn().isBefore(java.time.LocalDate.now())) {
			
			throw new BookingValidationException("Check-in date cannot be in the past");
		}
		
		if(!booking.getCheckOut().isAfter(booking.getCheckIn())) {
			
			throw new BookingValidationException("Check-out date must be after check-in date");
		}
		
		if(hotel.getAvailableRooms() <= 0) { 
			throw new RuntimeException("No Room Available");
		}
		
		long nights = java.time.temporal.ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
		
		
		Double totalAmount = hotel.getPricePerNight() * nights;
		
		booking.setTotalAmount(totalAmount);
		booking.setStatus("PENDING");
		booking.setHotel(hotel);
		
		hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
		
		hotelRepository.save(hotel);
		
		return bookingRepository.save(booking);
	}
	
	public List<Booking> getAllBookings(){
		return bookingRepository.findAll();
	}
	
	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));

	}
	
	@Transactional
	public Booking cancelBooking(Long id) {
		
		Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Find"));
		
		if("cancelled".equalsIgnoreCase(booking.getStatus())) {
			throw new BookingValidationException("Booking is Already Cancelled");
		}
		
		Hotel hotel = booking.getHotel();
		
		hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
		
		booking.setStatus("CANCELLED");
		
		hotelRepository.save(hotel);
		
		return bookingRepository.save(booking);
	}
	
	

}
