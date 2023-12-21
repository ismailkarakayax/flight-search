package com.aviation.flightsearch.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aviation.flightsearch.dto.UserDTO;
import com.aviation.flightsearch.entity.RefreshToken;
import com.aviation.flightsearch.entity.User;
import com.aviation.flightsearch.repository.RefreshTokenRepository;
import com.aviation.flightsearch.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
	
	@Value("${refresh.token.expires.in}")
	Long expireSeconds;
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final ModelMapper modelMapper;

	public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, ModelMapper modelMapper) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public String createRefreshToken(UserDTO user) {
		RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
		if(token == null) {
			token =	new RefreshToken();
			token.setUser(modelMapper.map(user, User.class));
		}
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
		refreshTokenRepository.save(token);
		return token.getToken();
	}

	@Override
	public boolean isRefreshExpired(RefreshToken token) {
		return token.getExpiryDate().before(new Date());
	}

	@Override
	public RefreshToken getByUserId(Long userId) {
		return refreshTokenRepository.findByUserId(userId);	
	}

}
