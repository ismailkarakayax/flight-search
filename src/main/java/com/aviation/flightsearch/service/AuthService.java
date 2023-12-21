package com.aviation.flightsearch.service;

import org.springframework.http.ResponseEntity;

import com.aviation.flightsearch.dto.request.LoginRequest;
import com.aviation.flightsearch.dto.request.RefreshRequest;
import com.aviation.flightsearch.dto.request.RegisterRequest;
import com.aviation.flightsearch.dto.response.AuthResponse;

public interface AuthService {
	ResponseEntity<AuthResponse> register(RegisterRequest request);
	ResponseEntity<AuthResponse> login(LoginRequest request);
	ResponseEntity<AuthResponse> refresh(RefreshRequest request);
}
