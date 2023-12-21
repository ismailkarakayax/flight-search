package com.aviation.flightsearch.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviation.flightsearch.dto.FlightDTO;
import com.aviation.flightsearch.dto.request.SearchRequest;
import com.aviation.flightsearch.service.FlightService;


@Tag(name = "Flight Controller", description = "API endpoints for flights")
@RestController
@RequestMapping(path = "/api/flights")
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

	@Operation(summary = "Create a new flight")
	@PostMapping
	public FlightDTO createFlight(@RequestBody FlightDTO flight) {
		return flightService.createFlight(flight);
	}

	@Operation(summary = "Get a flight by ID")
	@GetMapping(path = "/{flightId}")
	public FlightDTO getFlight(
			@Parameter(description = "ID of the flight", required = true)
			@PathVariable Long flightId) {
		return flightService.getFlightById(flightId);
	}

	@Operation(summary = "Get all flights")
	@GetMapping
	public List<FlightDTO> getAllFlights() {
		return flightService.getAllFlights();
	}

	@Operation(summary = "Update a flight by ID")
	@PutMapping(path = "/{flightId}")
	public FlightDTO updateFlight(
			@Parameter(description = "ID of the flight", required = true)
			@PathVariable Long flightId,
			@RequestBody FlightDTO newFlight) {
		return flightService.updateFlight(flightId, newFlight);
	}

	@Operation(summary = "Delete a flight by ID")
	@DeleteMapping(path = "/{flightId}")
	public void deleteAirportById(
			@Parameter(description = "ID of the flight", required = true)
			@PathVariable Long flightId) {
		flightService.deleteFlightById(flightId);
	}

	@Operation(summary = "Search for suited flights")
	@PostMapping(path = "/searchSuitedFlights")
	public List<List<FlightDTO>> searchSuitedFlights(@RequestBody SearchRequest request) {
		return flightService.searchSuitedFlights(request);
	}

	@Operation(summary = "Set flights externally")
	@PutMapping(path = "/setFlights")
	public void setFlightsExternally() {
		flightService.setFlightsExternally();
	}
}
