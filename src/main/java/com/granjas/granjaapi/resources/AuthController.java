package com.granjas.granjaapi.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.granjas.granjaapi.services.TokenService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	private final TokenService tokenService;
	
	public AuthController(TokenService tokenService) { 
		this.tokenService = tokenService;
	}
	
}
