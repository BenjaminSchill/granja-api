package com.granjas.granjaapi.services;

import java.util.List;
import java.util.Optional;

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
	
	public Weighing findById(Long id) { 
		Optional<Weighing> obj = weighingRepository.findById(id);
		return obj.get();
	}
	
	public Weighing insert(Weighing weighing) { 
		return weighingRepository.save(weighing);
	}
}
