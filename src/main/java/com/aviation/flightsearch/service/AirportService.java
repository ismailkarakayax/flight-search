package com.aviation.flightsearch.service;

import java.util.List;

import com.aviation.flightsearch.dto.AirportDTO;

public interface AirportService {
	AirportDTO createAirport(AirportDTO airport);
	List<AirportDTO> getAllAirports();
	AirportDTO getAirportById(Long airportId);
	AirportDTO updateAirport(Long airportId, AirportDTO airport);
	void deleteAirportById(Long airportId);
}
