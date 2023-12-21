package com.aviation.flightsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aviation.flightsearch.dto.FlightDTO;
import com.aviation.flightsearch.dto.request.SearchRequest;
import com.aviation.flightsearch.entity.Flight;
import com.aviation.flightsearch.helper.FlightSearchHelper;
import com.aviation.flightsearch.repository.FlightRepository;
import com.aviation.flightsearch.service.FlightService;

@Service
@EnableScheduling
public class FlightServiceImpl implements FlightService {
	
	private final FlightRepository flightRepository;
	private final ModelMapper modelMapper;
	
	public FlightServiceImpl(FlightRepository flightRepository, ModelMapper modelMapper) {
		this.flightRepository = flightRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public FlightDTO createFlight(FlightDTO newFlight) {
		Optional<Flight> flight = flightRepository.findById(newFlight.getId());
		if (flight.isPresent()) {
			throw new RuntimeException("Flight with this id already exists");
		}
		
		flightRepository.save(modelMapper.map(newFlight, Flight.class));
		return newFlight;
	}

	@Override
	public List<FlightDTO> getAllFlights() {
		List<Flight> flights = flightRepository.findAll();
		List<FlightDTO> DTOs = flights.stream().map(flight -> modelMapper.map(flight, FlightDTO.class)).collect(Collectors.toList());
		return DTOs;
	}

	@Override
	public FlightDTO getFlightById(Long flightId) {
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (flight.isPresent()) {
			return modelMapper.map(flight.get(), FlightDTO.class);
		}
		return null;
	}

	@Override
	public FlightDTO updateFlight(Long flightId, FlightDTO newFlight) {
		Optional<Flight> flight = flightRepository.findById(flightId);
		
		if (flight.isPresent()) {
			Flight foundFlight = flight.get();
			foundFlight.setDepartureAirport(newFlight.getDepartureAirport());
			foundFlight.setArrivalAirport(newFlight.getArrivalAirport());
			foundFlight.setDepartureDate(newFlight.getDepartureDate());
			foundFlight.setReturnDate(newFlight.getReturnDate());
			foundFlight.setPrice(newFlight.getPrice());
			
			flightRepository.save(foundFlight);
			return modelMapper.map(foundFlight, FlightDTO.class);
		}
		return null;
	}

	@Override
	public void deleteFlightById(Long flightId) {
		try {
			flightRepository.deleteById(flightId);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("Flight " + flightId + "does not exist");
		}
	}
	
	@Override
	public List<List<FlightDTO>> searchSuitedFlights(SearchRequest request) {
		List<List<FlightDTO>> suitedFlights = new ArrayList<>();
		List<Flight> allFlights = flightRepository.findAll();
		
		List<FlightDTO> depDTOs = FlightSearchHelper.findOneWayFlights(request, allFlights, modelMapper);
		suitedFlights.add(depDTOs);
		
		Boolean bothWays = request.getReturnDate() != null ? true : false;		
		
		if (bothWays) {
			List<FlightDTO> rtnDTOs = FlightSearchHelper.findTwoWayFlights(request, allFlights, modelMapper);
			suitedFlights.add(rtnDTOs);
		}
		
		return suitedFlights;
	}

	@Override
	@Scheduled(initialDelayString = "PT24H", fixedDelayString = "PT24H")
	public void setFlightsExternally() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Flight>> response = restTemplate.exchange(
		"http://localhost:8080/api/mock/getExternalFlights",
		HttpMethod.GET,
		null,
		new ParameterizedTypeReference<List<Flight>>(){});
		List<Flight> newFlights = response.getBody();
		
		flightRepository.deleteAll();
		flightRepository.saveAll(newFlights);
	}
}
