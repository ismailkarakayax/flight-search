package com.aviation.flightsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviation.flightsearch.dto.request.LoginRequest;
import com.aviation.flightsearch.dto.request.RefreshRequest;
import com.aviation.flightsearch.dto.request.RegisterRequest;
import com.aviation.flightsearch.dto.response.AuthResponse;
import com.aviation.flightsearch.service.AuthService;

@RestController
@RequestMapping(path = "/api/auth")
@Tag(name = "Authentication Controller", description = "Endpoints for User Authentication")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	@Operation(summary = "User Registration", description = "Register a new user.")
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
		// Method to handle user registration
		return authService.register(registerRequest);
	}

	@Operation(summary = "User Login", description = "Authenticate user and generate an access token.")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		// Method to handle user login
		return authService.login(loginRequest);
	}

	@Operation(summary = "Refresh Access Token", description = "Refresh the access token using the refresh token.")
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
		// Method to handle access token refresh
		return authService.refresh(refreshRequest);
	}
}
