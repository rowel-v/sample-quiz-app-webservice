package com.example.quizApp.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final static String MY_SECTRET_KEY = "rowel_Slim_shady_911_12345678910";

	private SecretKey key() {
		return Keys.hmacShaKeyFor(MY_SECTRET_KEY.getBytes());
	}
	public String generateToken(String username) {

		return Jwts.builder()
				.signWith(key())
				.subject(username)
				.expiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
				.compact();
	}

	public String extractUsername(String token) {

		return Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(token)
				.getPayload().getSubject();
	}

	public boolean validateToken(String token) {

		try {
			Jwts.parser()
			.verifyWith(key())
			.build()
			.parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			// Token is invalid (signature invalid, expired, malformed, etc.)
			return false;
		}
	}



}
