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
	private final EmailService emailService;
	
	public UserService(UserRepository userRepository, VerificationTokenService verificationTokenService,
			PasswordEncoder passwordEncoder, EmailService emailService) {
		
		this.userRepository = userRepository;
		this.verificationTokenService = verificationTokenService;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}
	
	@Transactional
	public User insert(User user) { 
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		var verificationToken = verificationTokenService.saveVerificationToken(user);
		
		String activationLink = "http://localhost:8080/auth/activate?token=" + verificationToken.getToken();
		
		String emailBody = "Hello " + user.getName() + ",\n\n"
				+ "Thank you for registering at BSGranjas\n"
				+ "Click the link below to activate your account. "
				+ "That link is valid for 15 minutes:\n\n"
				+ activationLink + "\n\n";
		
		 emailService.sendSimpleEmail(
				 user.getEmail(),
				 "BSGranjas - Account Activation Required",
				 emailBody);
		
		return user;
	}
}
