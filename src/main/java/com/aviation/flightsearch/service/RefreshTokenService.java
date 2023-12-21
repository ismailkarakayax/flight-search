package com.aviation.flightsearch.service;

import com.aviation.flightsearch.dto.UserDTO;
import com.aviation.flightsearch.entity.RefreshToken;

public interface RefreshTokenService {
	String createRefreshToken(UserDTO user);
	boolean isRefreshExpired(RefreshToken token);
	RefreshToken getByUserId(Long userId);
}
