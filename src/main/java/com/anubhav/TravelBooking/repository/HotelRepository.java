package com.anubhav.TravelBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.TravelBooking.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	List<Hotel> findByCity(String city);

}
