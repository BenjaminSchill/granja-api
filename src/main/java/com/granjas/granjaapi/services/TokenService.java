package com.granjas.granjaapi.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.granjas.granjaapi.config.UserSS;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	@Value("${api.token.expiration}")
	private Long expiration;
	
	public String generateToken(UserSS userSS) { 
		String token = JWT.create()
				.withIssuer("granja-api")
				.withSubject(userSS.getUsername())
				.withExpiresAt(Instant.now().plusMillis(expiration))
				.sign(Algorithm.HMAC256(secret));
		
		return token;
	}
}
