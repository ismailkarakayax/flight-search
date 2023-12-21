package com.aviation.flightsearch.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aviation.flightsearch.dto.UserDTO;
import com.aviation.flightsearch.dto.request.LoginRequest;
import com.aviation.flightsearch.dto.request.RefreshRequest;
import com.aviation.flightsearch.dto.request.RegisterRequest;
import com.aviation.flightsearch.dto.response.AuthResponse;
import com.aviation.flightsearch.entity.RefreshToken;
import com.aviation.flightsearch.entity.User;
import com.aviation.flightsearch.security.JwtTokenProvider;
import com.aviation.flightsearch.service.AuthService;
import com.aviation.flightsearch.service.RefreshTokenService;
import com.aviation.flightsearch.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenService refreshTokenService;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, 
    		PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

	@Override
	public ResponseEntity<AuthResponse> register(RegisterRequest request) {
		
		AuthResponse response = new AuthResponse();
		if(userService.getUserByUsername(request.getUsername()) != null) {
			response.setMessage("Username already in use.");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

		request.setPassword(passwordEncoder.encode(request.getPassword()));
		UserDTO newUser = userService.createUser(request);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		response.setMessage("User successfully registered.");
		response.setAccessToken("Bearer " + jwtToken);
		response.setRefreshToken(refreshTokenService.createRefreshToken(newUser));
		response.setUserId(newUser.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<AuthResponse> login(LoginRequest request) {
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		
		AuthResponse response = new AuthResponse();
		
		UserDTO user = userService.getUserByUsername(request.getUsername());
		response.setAccessToken("Bearer " + jwtToken);
		response.setRefreshToken(refreshTokenService.createRefreshToken(user));
		response.setUserId(user.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AuthResponse> refresh(RefreshRequest request) {
		
		AuthResponse response = new AuthResponse();
		RefreshToken token = refreshTokenService.getByUserId(request.getId());
		if(token.getToken().equals(request.getRefreshToken()) && !refreshTokenService.isRefreshExpired(token)) {
			User user = token.getUser();
			String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
			response.setMessage("Token successfully refreshed.");
			response.setAccessToken("Bearer " + jwtToken);
			response.setUserId(user.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);		
		} 

		response.setMessage("Refresh token is not valid.");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

}
