package com.granjas.granjaapi.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.User;
import com.granjas.granjaapi.entities.VerificationToken;
import com.granjas.granjaapi.repositories.VerificationTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class VerificationTokenService {
	
	private final VerificationTokenRepository verificationTokenRepository;

	public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
		this.verificationTokenRepository = verificationTokenRepository;
	}
	
	@Transactional
	public VerificationToken saveVerificationToken(User user) { 
		String randomToken = UUID.randomUUID().toString();
		Instant expiryDate = Instant.now().plus(15, ChronoUnit.MINUTES);
		
		VerificationToken verToken = new VerificationToken(null, randomToken, user, expiryDate);
		verificationTokenRepository.save(verToken);
		return verToken;
	}
	
}
