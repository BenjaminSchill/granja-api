package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.Weighing;
import com.granjas.granjaapi.repositories.WeighingRepository;

@Service
public class WeighingService {
	
	private final WeighingRepository weighingRepository;

	public WeighingService(WeighingRepository weighingRepository) { 
		this.weighingRepository = weighingRepository;
	}
	
	public List<Weighing> findAll() { 
		return weighingRepository.findAll();
	}
}
