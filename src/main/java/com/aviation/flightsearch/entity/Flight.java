package com.aviation.flightsearch.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "flight")
@Data
public class Flight {
	@Id
	@Column(name="flight-id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	private Airport departureAirport;
	@ManyToOne(cascade = CascadeType.ALL)
	private Airport arrivalAirport;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime departureDate;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime returnDate;
	private BigDecimal price;
}
