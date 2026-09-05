package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.repositories.FarmRepository;
import com.granjas.granjaapi.services.exceptions.DatabaseException;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class FarmService {
	
	private final FarmRepository farmRepository;

	public FarmService(FarmRepository farmRepository) { 
		this.farmRepository = farmRepository;
	}
	
	public List<Farm> findAll() { 
		return farmRepository.findAll();
	}
	
	public Farm findById(Long id) { 
		return farmRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
	}
	
	public Farm insert(Farm farm) { 
		return farmRepository.save(farm);
	}
	
	@Transactional
	public Farm update(Long id, Farm farm) { 
		Farm entity = farmRepository.getReferenceById(id);
		entity.setName(farm.getName());
		entity.setCapacity(farm.getCapacity());
		return farmRepository.save(entity);
	}
	
	public void delete(Long id) { 
		try { 
			farmRepository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) { 
			throw new DatabaseException(e.getMessage());
		}
	}
}
