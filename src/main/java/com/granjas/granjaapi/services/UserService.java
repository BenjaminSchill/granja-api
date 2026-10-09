package com.granjas.granjaapi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.User;
import com.granjas.granjaapi.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final VerificationTokenService verificationTokenService;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, VerificationTokenService verificationTokenService,
			PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.verificationTokenService = verificationTokenService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public User insert(User user) { 
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		verificationTokenService.saveVerificationToken(user);
		return user;
	}
}
