package com.anubhav.TravelBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anubhav.TravelBooking.entity.Hotel;
import com.anubhav.TravelBooking.service.HotelService;

@RestController
public class HotelController {
	
	private final HotelService hotelService;
	
	public HotelController(HotelService hotelService) {
		this.hotelService = hotelService;
	}
	
	@PostMapping("api/hotels")
	public Hotel addHotel(@RequestBody Hotel hotel) {
		return hotelService.addHotel(hotel);
	}
	
	@GetMapping("/api/hotels")
	public List<Hotel> getAllHotels(){
		return hotelService.getAllHotels();
	}
	
	@GetMapping("api/hotels/search")
	public List<Hotel> searchHotels(@RequestParam String city){
		return hotelService.searchHotelsByCity(city);
	}

	
}
