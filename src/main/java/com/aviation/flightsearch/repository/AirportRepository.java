package com.aviation.flightsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviation.flightsearch.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long>{

}
