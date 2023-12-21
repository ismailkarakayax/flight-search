package com.aviation.flightsearch.dto.request;

import java.time.LocalDateTime;

import com.aviation.flightsearch.entity.Airport;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SearchRequest {
	private Airport departureAirport;
	private Airport arrivalAirport;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime departureDate;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime returnDate;
}
