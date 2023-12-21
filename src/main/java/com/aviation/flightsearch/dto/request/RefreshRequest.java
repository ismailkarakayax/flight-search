package com.aviation.flightsearch.dto.request;

import lombok.Data;

@Data
public class RefreshRequest {
	Long id;
	String refreshToken;
}
