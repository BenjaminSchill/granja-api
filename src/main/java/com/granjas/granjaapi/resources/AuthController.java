package com.granjas.granjaapi.resources;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.granjas.granjaapi.config.UserSS;
import com.granjas.granjaapi.dto.CredentialsDTO;
import com.granjas.granjaapi.dto.UserDTO;
import com.granjas.granjaapi.entities.User;
import com.granjas.granjaapi.entities.enums.Role;
import com.granjas.granjaapi.services.TokenService;
import com.granjas.granjaapi.services.UserDetailsServiceImpl;
import com.granjas.granjaapi.services.UserService;
import com.granjas.granjaapi.services.VerificationTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	private final TokenService tokenService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final VerificationTokenService verificationTokenService;
	
	public AuthController(TokenService tokenService, UserDetailsServiceImpl userDetailsServiceImpl,
			PasswordEncoder passwordEncoder, UserService userService, VerificationTokenService verificationTokenService) { 
		
		this.tokenService = tokenService;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.verificationTokenService = verificationTokenService;
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<User> register(@Valid @RequestBody UserDTO dto) { 
		User user = new User(null, dto.getName(), dto.getEmail(), dto.getPassword(), Role.USER, false, false);
		user = userService.insert(user);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody CredentialsDTO credentialsDTO) { 		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(credentialsDTO.getEmail());

		if (!passwordEncoder.matches(credentialsDTO.getPassword(), userDetails.getPassword())) { 
			return ResponseEntity.status(401).body("Invalid email or password");
		}
		
		if (!userDetails.isEnabled()) {
			return ResponseEntity.status(403).body("Account not activated. Please check your email.");
		}
		
		if (!userDetails.isAccountNonLocked()) {
			return ResponseEntity.status(403).body("This account has been deleted or deactivated.");
		}
		
		UserSS userSS = (UserSS) userDetails;
		
		String token = tokenService.generateToken(userSS);
		
		return ResponseEntity.ok().body(token);
	}
	
	@GetMapping(value = "/activate")
	public ResponseEntity<String> activate(@RequestParam String token) { 
		verificationTokenService.activateAccount(token);
		return ResponseEntity.ok("Account activated sucessfully");
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
