package com.aviation.flightsearch.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component	
public class JwtTokenProvider {
	
	@Value("${flightsearch.app.secret}")
	private String APP_SECRET;
	@Value("${flightsearch.expires.in}")
	private Long EXPIRES_IN;
	
	public String generateJwtToken(Authentication auth) {
		JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
 		return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
 				.setIssuedAt(new Date()).setExpiration(expireDate)
 				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}
	
	public String generateJwtTokenByUserId(Long userId) {
		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().setSubject(Long.toString(userId))
				.setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}
	
	Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
	
	boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
			return !isExpiredToken(token);
		}
		catch (Exception e) {
			return false;
		}
	}
	
	boolean isExpiredToken(String token) {
		Date expr = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
		return expr.before(new Date());
	}
}
