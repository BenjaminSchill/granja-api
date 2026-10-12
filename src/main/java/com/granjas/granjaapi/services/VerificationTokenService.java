package com.granjas.granjaapi.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.User;
import com.granjas.granjaapi.entities.VerificationToken;
import com.granjas.granjaapi.repositories.UserRepository;
import com.granjas.granjaapi.repositories.VerificationTokenRepository;
import com.granjas.granjaapi.services.exceptions.BusinessRuleException;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class VerificationTokenService {
	
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserRepository userRepository;
	
	public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public VerificationToken saveVerificationToken(User user) { 
		String randomToken = UUID.randomUUID().toString();
		Instant expiryDate = Instant.now().plus(15, ChronoUnit.MINUTES);
		
		VerificationToken verToken = new VerificationToken(null, randomToken, user, expiryDate);
		verificationTokenRepository.save(verToken);
		return verToken;
	}
	
	@Transactional
	public void activateAccount(String token) { 
		VerificationToken temporaryToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid verification token: " + token));
		
		if (Instant.now().isAfter(temporaryToken.getExpiryDate())) { 
			throw new BusinessRuleException("Your token has expired");
		}
		
		User user = temporaryToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
	}
	
}
