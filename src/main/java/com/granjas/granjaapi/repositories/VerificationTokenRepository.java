package com.granjas.granjaapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.granjas.granjaapi.entities.VerificationToken;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	
	Optional<VerificationToken> findByToken(String token);
	
}
