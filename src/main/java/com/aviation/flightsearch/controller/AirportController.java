package com.aviation.flightsearch.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviation.flightsearch.dto.AirportDTO;
import com.aviation.flightsearch.service.AirportService;

@RestController
@RequestMapping(path = "/api/airports")
@Tag(name = "Airport Operations", description = "Endpoints for managing airports")
public class AirportController {

	private final AirportService airportService;

	public AirportController(AirportService airportService) {
		this.airportService = airportService;
	}

	@PostMapping
	@Operation(summary = "Create Airport", description = "Creates a new airport.")
	public AirportDTO createAirport(@RequestBody AirportDTO airport) {
		// Method to create a new airport
		return airportService.createAirport(airport);
	}

	@GetMapping(path = "/{airportId}")
	@Operation(summary = "Get Airport", description = "Retrieves information about a specific airport.")
	public AirportDTO getAirport(@PathVariable Long airportId) {
		// Method to retrieve information about a specific airport by ID
		return airportService.getAirportById(airportId);
	}

	@GetMapping
	@Operation(summary = "Get All Airports", description = "Retrieves a list of all airports.")
	public List<AirportDTO> getAllAirports() {
		// Method to retrieve a list of all airports
		return airportService.getAllAirports();
	}

	@PutMapping(path = "/{airportId}")
	@Operation(summary = "Update Airport", description = "Updates information about a specific airport.")
	public AirportDTO updateAirport(@PathVariable Long airportId, @RequestBody AirportDTO newAirport) {
		// Method to update information about a specific airport by ID
		return airportService.updateAirport(airportId, newAirport);
	}

	@DeleteMapping(path = "/{airportId}")
	@Operation(summary = "Delete Airport", description = "Deletes a specific airport.")
	public void deleteAirportById(@PathVariable Long airportId) {
		// Method to delete a specific airport by ID
		airportService.deleteAirportById(airportId);
	}
}
