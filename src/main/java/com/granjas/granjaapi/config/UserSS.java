package com.granjas.granjaapi.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSS implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String password;
	private boolean enabled;
	private boolean deleted;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS() { 
	}
	
	public UserSS(Long id, String email, String password, boolean enabled, boolean deleted,
			Collection<? extends GrantedAuthority> authorities) {
		
		this.id = id;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.deleted = deleted;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !deleted;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
