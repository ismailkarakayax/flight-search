package com.aviation.flightsearch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "airport")
@Data
public class Airport {
	@Id
	@Column(name="airport-id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String city;
}
