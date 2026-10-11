package com.anubhav.TravelBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anubhav.TravelBooking.entity.Hotel;
import com.anubhav.TravelBooking.repository.HotelRepository;

@Service
public class HotelService {
	
	private final HotelRepository hotelRepository;

	public HotelService(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}
	
	public Hotel addHotel(Hotel hotel) {
		
		return hotelRepository.save(hotel);
	}
	
	public List<Hotel> getAllHotels(){
		return hotelRepository.findAll();
	}
	
	public List<Hotel> searchHotelsByCity(String city){
		return hotelRepository.findByCity(city);
	}
}
