package com.granjas.granjaapi.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.granjas.granjaapi.entities.User;
import com.granjas.granjaapi.repositories.UserRepository;
import com.granjas.granjaapi.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	private final TokenService tokenService;
	private final UserRepository userRepository;
	
	public SecurityFilter(TokenService tokenService, UserRepository userRepository) { 
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recoverToken(request);
		
		if (token != null) { 
			String email = tokenService.validateToken(token);
			Optional<User> user = userRepository.findByEmail(email);
			
			UserSS userSS = new UserSS(
					user.get().getId(),
					user.get().getEmail(),
					user.get().getPassword(),
					user.get().isEnabled(),
					user.get().isDeleted(),
					List.of(new SimpleGrantedAuthority("ROLE_" + user.get().getRole().name()))
					);
			var auth = new UsernamePasswordAuthenticationToken(userSS, null, userSS.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
	}
	
	private String recoverToken(HttpServletRequest request) { 
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) { 
			return null;
		}
		return authHeader.replace("Bearer ", "");
	}
}
