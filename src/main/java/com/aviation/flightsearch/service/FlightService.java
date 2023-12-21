package com.aviation.flightsearch.service;

import java.util.List;

import com.aviation.flightsearch.dto.FlightDTO;
import com.aviation.flightsearch.dto.request.SearchRequest;

public interface FlightService {
	FlightDTO createFlight(FlightDTO flight);
	List<FlightDTO> getAllFlights();
	FlightDTO getFlightById(Long flightId);
	FlightDTO updateFlight(Long flightId, FlightDTO flight);
	void deleteFlightById(Long flightId);
	
	List<List<FlightDTO>> searchSuitedFlights(SearchRequest request);
	
	void setFlightsExternally();
}
