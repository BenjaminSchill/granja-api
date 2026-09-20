package com.granjas.granjaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.granjas.granjaapi.services.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
	
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) { 
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

		return http.build();
	}
}
