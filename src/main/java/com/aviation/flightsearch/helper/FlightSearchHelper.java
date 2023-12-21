package com.aviation.flightsearch.helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.aviation.flightsearch.dto.FlightDTO;
import com.aviation.flightsearch.dto.request.SearchRequest;
import com.aviation.flightsearch.entity.Airport;
import com.aviation.flightsearch.entity.Flight;

public class FlightSearchHelper {
	public static List<FlightDTO> findOneWayFlights(SearchRequest request, List<Flight> allFlights, ModelMapper modelMapper) {
		Airport reqDepartureAirport = request.getDepartureAirport();
		Airport reqArrivalAirport = request.getArrivalAirport();
		LocalDateTime reqDepartureDate = request.getDepartureDate();
		
		List<Flight> depFlights = new ArrayList<>();
		depFlights = 
			allFlights
			.stream()
			.filter(f -> Objects.equals(f.getDepartureAirport().getId(), reqDepartureAirport.getId()) &&
                    Objects.equals(f.getArrivalAirport().getId(), reqArrivalAirport.getId()) &&
						 f.getDepartureDate().getYear() == reqDepartureDate.getYear() &&
						 f.getDepartureDate().getMonthValue() == reqDepartureDate.getMonthValue() &&
						 f.getDepartureDate().getDayOfMonth() == reqDepartureDate.getDayOfMonth())
			.collect(Collectors.toList());
		
		List<FlightDTO> depDTOs = depFlights.stream().map(f -> modelMapper.map(f, FlightDTO.class)).collect(Collectors.toList());
		return depDTOs;
	}
	
	public static List<FlightDTO> findTwoWayFlights(SearchRequest request, List<Flight> allFlights, ModelMapper modelMapper) {
		Airport reqDepartureAirport = request.getDepartureAirport();
		Airport reqArrivalAirport = request.getArrivalAirport();
		LocalDateTime reqReturnDate = request.getReturnDate();
		
		List<Flight> rtnFlights = new ArrayList<>();
		rtnFlights = 
			allFlights
			.stream()
			.filter(f -> Objects.equals(f.getDepartureAirport().getId(), reqArrivalAirport.getId()) &&
                    Objects.equals(f.getArrivalAirport().getId(), reqDepartureAirport.getId()) &&
						 f.getDepartureDate().getYear() == reqReturnDate.getYear() &&
						 f.getDepartureDate().getMonthValue() == reqReturnDate.getMonthValue() &&
						 f.getDepartureDate().getDayOfMonth() == reqReturnDate.getDayOfMonth())
			.collect(Collectors.toList());

		List<FlightDTO> rtnDTOs = rtnFlights.stream().map(f -> modelMapper.map(f, FlightDTO.class)).collect(Collectors.toList());
		return rtnDTOs;
	}
}
