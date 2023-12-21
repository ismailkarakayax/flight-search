package com.aviation.flightsearch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.aviation.flightsearch.dto.AirportDTO;
import com.aviation.flightsearch.entity.Airport;
import com.aviation.flightsearch.repository.AirportRepository;
import com.aviation.flightsearch.service.AirportService;

@Service
public class AirportServiceImpl implements AirportService {
	
	private final AirportRepository airportRepository;
	private final ModelMapper modelMapper;
	
	public AirportServiceImpl(AirportRepository airportRepository, ModelMapper modelMapper) {
		this.airportRepository = airportRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public AirportDTO createAirport(AirportDTO newAirport) {
		Optional<Airport> airport = airportRepository.findById(newAirport.getId());
		if (airport.isPresent()) {
			throw new RuntimeException("Airport with this id already exists");
		}
		
		airportRepository.save(modelMapper.map(newAirport, Airport.class));
		return newAirport;
	}

	@Override
	public List<AirportDTO> getAllAirports() {
		List<Airport> airports = airportRepository.findAll();
		List<AirportDTO> DTOs = airports.stream().map(airport -> modelMapper.map(airport, AirportDTO.class)).collect(Collectors.toList());
		return DTOs;
	}

	@Override
	public AirportDTO getAirportById(Long airportId) {
		Optional<Airport> airport = airportRepository.findById(airportId);
		if (airport.isPresent()) {
			return modelMapper.map(airport.get(), AirportDTO.class);
		}
		return null;
	}

	@Override
	public AirportDTO updateAirport(Long airportId, AirportDTO newAirport) {
		Optional<Airport> airport = airportRepository.findById(airportId);
		
		if (airport.isPresent()) {
			Airport foundAirport = airport.get();
			foundAirport.setCity(newAirport.getCity());
			
			airportRepository.save(foundAirport);
			return modelMapper.map(foundAirport, AirportDTO.class);
		}
		return null;
	}

	@Override
	public void deleteAirportById(Long airportId) {
		try {
			airportRepository.deleteById(airportId);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("Airport " + airportId + "does not exist");
		}
	}

}
