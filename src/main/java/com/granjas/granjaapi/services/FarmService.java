package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.repositories.FarmRepository;

@Service
public class FarmService {
	
	private final FarmRepository farmRepository;

	public FarmService(FarmRepository farmRepository) { 
		this.farmRepository = farmRepository;
	}
	
	public List<Farm> findAll() { 
		return farmRepository.findAll();
	}
}
