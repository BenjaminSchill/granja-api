package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.granjas.granjaapi.config.UserSS;
import com.granjas.granjaapi.entities.User;
import com.granjas.granjaapi.repositories.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) { 
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow();
		
		UserSS userSS = new UserSS(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
		
		return userSS;
	}
}
