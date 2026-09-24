package com.granjas.granjaapi.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.granjas.granjaapi.config.UserSS;
import com.granjas.granjaapi.dto.CredentialsDTO;
import com.granjas.granjaapi.services.TokenService;
import com.granjas.granjaapi.services.UserDetailsServiceImpl;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	private final TokenService tokenService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final PasswordEncoder passwordEncoder;
	
	public AuthController(TokenService tokenService, UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) { 
		this.tokenService = tokenService;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody CredentialsDTO credentialsDTO) { 		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(credentialsDTO.getEmail());

		if (!passwordEncoder.matches(credentialsDTO.getPassword(), userDetails.getPassword())) { 
			return ResponseEntity.status(401).body("Invalid email or password");
		}
		
		UserSS userSS = (UserSS) userDetails;
		
		String token = tokenService.generateToken(userSS);
		
		return ResponseEntity.ok().body(token);
	}
}
