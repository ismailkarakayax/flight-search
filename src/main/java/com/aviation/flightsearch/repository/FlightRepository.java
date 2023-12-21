package com.aviation.flightsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviation.flightsearch.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{

}
