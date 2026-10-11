package com.anubhav.TravelBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anubhav.TravelBooking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
