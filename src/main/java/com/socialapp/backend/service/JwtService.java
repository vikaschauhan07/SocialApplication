package com.socialapp.backend.service;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
	String generateToken(UserDetails userDetails);

	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);

//	String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);

	String generateRefreshToken(UserDetails userDetails);

	String extractUsername(String token);
}
