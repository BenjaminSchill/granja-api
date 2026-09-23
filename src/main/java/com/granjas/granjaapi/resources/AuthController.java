package com.granjas.granjaapi.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.granjas.granjaapi.config.UserSS;
import com.granjas.granjaapi.dto.CredentialsDTO;
import com.granjas.granjaapi.services.TokenService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	private final TokenService tokenService;
	private final AuthenticationManager authenticationManager;
	
	public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) { 
		this.tokenService = tokenService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody CredentialsDTO credentialsDTO) { 
		UsernamePasswordAuthenticationToken authenticator =
				new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(authenticator);
		
		UserSS userSS = (UserSS) authentication.getPrincipal();
		
		String token = tokenService.generateToken(userSS);
		
		return ResponseEntity.ok().body(token);
	}
}
