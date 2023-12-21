package com.aviation.flightsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviation.flightsearch.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	RefreshToken findByUserId(Long userId);
}
