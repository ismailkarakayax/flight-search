package com.aviation.flightsearch.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aviation.flightsearch.entity.Airport;

import lombok.Data;

@Data
public class FlightDTO {
	private Long id;
	private Airport departureAirport;
	private Airport arrivalAirport;
	private LocalDateTime departureDate;
	private LocalDateTime returnDate;
	private BigDecimal price;
}
